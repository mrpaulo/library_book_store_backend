/*
 * Copyright (C) 2021 paulo.rodrigues
 * Profile: <https://github.com/mrpaulo>
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
package com.paulo.rodrigues.librarybookstore.publisher.controller;

import com.paulo.rodrigues.librarybookstore.publisher.dto.PublisherDTO;
import com.paulo.rodrigues.librarybookstore.utils.LibraryStoreBooksException;
import com.paulo.rodrigues.librarybookstore.publisher.filter.PublisherFilter;
import com.paulo.rodrigues.librarybookstore.publisher.model.Publisher;
import com.paulo.rodrigues.librarybookstore.publisher.model.Publisher;
import com.paulo.rodrigues.librarybookstore.publisher.service.PublisherService;
import com.paulo.rodrigues.librarybookstore.utils.FormatUtils;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author paulo.rodrigues
 */
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @GetMapping("/all")
    public List<PublisherDTO> getAll() {
        List<PublisherDTO> publishersList = publisherService.findAll();

        return publishersList.stream().sorted(Comparator.comparing(PublisherDTO::getName)).collect(Collectors.toList());
    }

    @PostMapping("/fetch")
    public List<PublisherDTO> getAllPageble(@RequestBody PublisherFilter filter, HttpServletRequest req, HttpServletResponse res) {
        Pageable pageable = FormatUtils.getPageRequest(filter);
        Page<Publisher> result = publisherService.findPageble(filter, pageable);
        res.addHeader("totalcount", String.valueOf(result.getTotalElements()));

        return publisherService.toListDTO(result.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getById(@PathVariable(value = "id") Long publisherId) throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(publisherService.findById(publisherId));
    }
    
    @GetMapping("/fetch/{name}")
    public ResponseEntity<List<PublisherDTO>> fetchByName(@PathVariable(value = "name") String name) throws LibraryStoreBooksException {
        return ResponseEntity.ok().body(publisherService.findByName(name));
    }

    @PostMapping()
    public PublisherDTO create(@RequestBody Publisher publisher) throws LibraryStoreBooksException {
        return publisherService.create(publisher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublisherDTO> update(@PathVariable(value = "id") Long publisherId, @RequestBody PublisherDTO publisherDetalhes) throws LibraryStoreBooksException {
        return ResponseEntity.ok(publisherService.edit(publisherId, publisherDetalhes));
    }

    @DeleteMapping("/{id}")
    public Map<String, Long> delete(@PathVariable(value = "id") Long publisherId) throws LibraryStoreBooksException {
        publisherService.erase(publisherId);
        Map<String, Long> response = new HashMap<>();
        response.put("id", publisherId);

        return response;
    }
}
