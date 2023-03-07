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
import com.paulo.rodrigues.librarybookstore.LibraryBookStoreApplication
import groovy.json.JsonOutput
import groovy.json.JsonParserType
import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import spock.lang.Specification
import groovy.json.JsonSlurper
import groovyx.net.http.RESTClient
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import spock.lang.Shared
import spock.lang.Unroll

import static com.paulo.rodrigues.librarybookstore.test.ObjectMother.nameForTest
import static groovyx.net.http.ContentType.URLENC
import static org.apache.http.HttpStatus.SC_OK

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
    ObjectMapper objectMapper
         
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
    }



    def createUser() {
        Map dbConnParams = [
                url: 'jdbc:postgresql://localhost:5432/test2_library_book_store',
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
        given:
        createUser()
        
        when: "a rest call is performed to the status page"
        def response = client.post(
            path : "/oauth/token",
            requestContentType : URLENC,
            body : [username: nameForTest, password:'1', grant_type:'password']
        )

        then: "the correct message is expected"
        response.status == SC_OK
        deleteUserAndClose() == null
    }

    def cleanupSpec() {
        def baseAuthorAPI = "/api/v1/authors"
        def nameToSearch = nameForTest
        def authorResponse = client.get(path : baseAuthorAPI + "/fetch/" + nameToSearch)

        if(authorResponse != null && authorResponse.responseData.size() > 0){
            authorResponse.responseData.forEach({ r ->
                client.delete(path: baseAuthorAPI + "/" + r.id)
            })
        }

        def baseAddressAPI = "/api/v1/addresses"
        def addressResponse = client.get(path : baseAddressAPI + "/" + nameToSearch + "/name")

        if(addressResponse != null && addressResponse.responseData.size() > 0){
            addressResponse.responseData.forEach({ r ->
                client.delete(path: baseAddressAPI + "/" + r.id)
            })
        }
    }
    
}

