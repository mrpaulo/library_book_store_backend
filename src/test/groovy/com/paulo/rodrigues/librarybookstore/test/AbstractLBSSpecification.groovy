/*
 * Copyright (C) 2022 paulo.rodrigues
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 *
 * @author paulo.rodrigues
 */
package com.paulo.rodrigues.librarybookstore.test

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.paulo.rodrigues.librarybookstore.LibraryBookStoreApplication
import groovy.sql.Sql
import org.apache.http.HttpResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import spock.lang.Specification
import groovyx.net.http.RESTClient
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import spock.lang.Shared
import spock.lang.Unroll

import static com.paulo.rodrigues.librarybookstore.test.ObjectMother.buildPublisher
import static com.paulo.rodrigues.librarybookstore.test.ObjectMother.nameForTest
import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.ContentType.URLENC
import static org.apache.http.HttpStatus.SC_OK
import static com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil.*;

@ActiveProfiles(["Test"])
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@TestPropertySource(        
        properties = [                
                "server.ssl.enabled: false",
                "security.basic.enabled: false",
                "security.ignored: /**"                
        ]
)
@SpringBootTest(classes = LibraryBookStoreApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AbstractLBSSpecification extends Specification {
	
    @LocalServerPort
    int port

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())

    @Shared
    def client

    @Shared
    def sql

    @Shared
    def userId = 99999

    def setup() {
        def username = "client"
        def password = "teste"
        client = new RESTClient("http://localhost:${port}")        
        client.headers['Authorization'] = "Basic ${"$username:$password".bytes.encodeBase64()}"
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
    }

    def createUser() {
        Map dbConnParams = [
                url: 'jdbc:postgresql://localhost:5432/library_book_store_db',
                user: 'postgres',
                password: 'postgres',
                driver: 'org.postgresql.Driver']

        sql = Sql.newInstance(dbConnParams)

        sql.execute "insert into lbs_user (id, name, username, password, email, create_by) \n" +
                "values (" +
                userId + ", '"+ nameForTest +"', '" + nameForTest + "', '{noop}1', '" + nameForTest + "@teste.com', 'Teste');"

        sql.execute "insert into user_role (user_id, role_id) values (" + userId  +",1);"
    }

    def deleteUserAndClose() {
        sql.execute "DELETE FROM user_role where user_id = " + userId + ";"
        sql.execute "DELETE FROM lbs_user where id = " + userId + ";"
        sql.close()
    }

    @Unroll
    def "Login - testing"() {
        given: "an user created on DB"
        createUser()
        
        when: "a rest call is performed to login"
        def response = client.post(
            path : "/oauth/token",
            requestContentType : URLENC,
            body : [username: nameForTest, password:'1', grant_type:'password']
        )

        then: "the correct message is expected"
        response.status == SC_OK

        and: "delete the user and close the DB connection"
        deleteUserAndClose() == null
    }

    def createItemOnDb(pathAPI, item) {
        postRestCall(pathAPI, item)
    }

    def getIdCreatedFromTest(pathAPI, nameToSearch) {
        def response = client.get(path : pathAPI + "/fetch/" + nameToSearch)
        return response.responseData.size > 0 ? response.responseData[0].id : null
    }

    HttpResponse deleteByIdRestCall(pathAPI, idToDelete) {
        client.delete(path : pathAPI + "/" + idToDelete)
    }

    HttpResponse getByNameRestCall (pathAPI, nameToSearch){
        return client.get(path : pathAPI + "/fetch/" + nameToSearch)
    }

    HttpResponse getByIdRestCall (pathAPI, id){
        return client.get(path : pathAPI + "/" + id)
    }

    HttpResponse getRestCall(pathAPI){
        return client.get(path : pathAPI)
    }

    HttpResponse postRestCall (pathAPI, item){
        return client.post(path : pathAPI,
                requestContentType : JSON,
                body : objectMapper.writeValueAsString(item)
        )
    }

    HttpResponse putWithIdRestCall(pathAPI, id, item){
        return client.put(path : pathAPI + "/" + id,
                requestContentType : JSON,
                body : objectMapper.writeValueAsString(item)
        )
    }

    def cleanupSpec() {
        def baseAuthorAPI = AUTHORS_V1_BASE_API
        def nameToSearch = nameForTest
        def authorResponse = getByNameRestCall(baseAuthorAPI, nameToSearch)

        if(authorResponse != null && authorResponse.responseData.size() > 0){
            authorResponse.responseData.forEach({ r ->
                deleteByIdRestCall(baseAuthorAPI, r.id)
            })
        }

        def baseAddressAPI = ADDRESSES_V1_BASE_API
        def addressResponse = getByNameRestCall(baseAddressAPI, nameToSearch)

        if(addressResponse != null && addressResponse.responseData.size() > 0){
            addressResponse.responseData.forEach({ r ->
                deleteByIdRestCall(baseAddressAPI, r.id)
            })
        }

        def basePublisherAPI = PUBLISHERS_V1_BASE_API
        def idToDelete2 = getIdCreatedFromTest(basePublisherAPI, buildPublisher().getName())
        if(idToDelete2)
            deleteByIdRestCall(basePublisherAPI, idToDelete2)
    }
    
}

