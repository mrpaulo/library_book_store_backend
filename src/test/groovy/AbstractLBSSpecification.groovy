
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
import com.fasterxml.jackson.databind.ObjectMapper
import com.paulo.rodrigues.librarybookstore.LibraryBookStoreApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import spock.lang.Specification
import groovy.json.JsonSlurper
import groovyx.net.http.RESTClient
import org.spockframework.spring.SpringBean
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import spock.lang.Shared
import spock.lang.Unroll
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
    
    def setup() {
        def username = "client"
        def password = "teste"
        client = new RESTClient("http://localhost:${port}")        
        client.headers['Authorization'] = "Basic ${"$username:$password".bytes.encodeBase64()}"
    }
    
    def slurper = new JsonSlurper()

    def toJson(object) {
        new ObjectMapper().writeValueAsString(object)
    }
    
    @Unroll
    def "Login - testing"() {             
        
        when: "a rest call is performed to the status page"
        def response = client.post(
            path : "/oauth/token",
            requestContentType : URLENC,
            body : [username:'paulo', password:'1', grant_type:'password']
        )

        then: "the correct message is expected"
        response.status == SC_OK
    }  

    
}

