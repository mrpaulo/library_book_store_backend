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
import com.paulo.rodrigues.librarybookstore.utils.*;
import com.paulo.rodrigues.librarybookstore.publisher.filter.PublisherFilter;
import com.paulo.rodrigues.librarybookstore.publisher.model.Publisher;
import com.paulo.rodrigues.librarybookstore.publisher.service.PublisherService;

import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

import static com.paulo.rodrigues.librarybookstore.utils.ConstantsUtil.*;

/**
 *
 * @author paulo.rodrigues
 */
@Log4j2
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping(PUBLISHERS_V1_BASE_API)
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @GetMapping(GET_ALL_PATH)
    public List<PublisherDTO> getAll() {
        List<PublisherDTO> publishersList = publisherService.findAll();

        return publishersList.stream().sorted(Comparator.comparing(PublisherDTO::getName)).collect(Collectors.toList());
    }

    @PostMapping(FIND_PAGEABLE_PATH)
    public List<PublisherDTO> findPageable(@RequestBody PublisherFilter filter, HttpServletRequest req, HttpServletResponse res) {
        try {
            Pageable pageable = FormatUtils.getPageRequest(filter);
            Page<Publisher> result = publisherService.findPageable(filter, pageable);
            res.addHeader("totalcount", String.valueOf(result.getTotalElements()));
            return publisherService.publishersToDTOs(result.getContent());
        } catch (Exception e) {
            log.error("Exception on findPageable PublisherFilter={}, message={}", filter, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @GetMapping(GET_BY_ID_PATH)
    public ResponseEntity<Publisher> getById(@PathVariable(value = "id") Long publisherId) throws LibraryStoreBooksException, NotFoundException {
        try {
            return ResponseEntity.ok().body(publisherService.findById(publisherId));
        } catch (Exception e) {
            log.error("Exception on getById publisherId={}, message={}", publisherId, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }
    
    @GetMapping(GET_BY_NAME_PATH)
    public ResponseEntity<List<PublisherDTO>> findByName(@PathVariable(value = "name") String publisherName) throws LibraryStoreBooksException, NotFoundException {
        try {
            return ResponseEntity.ok().body(publisherService.findByName(publisherName));
        } catch (Exception e) {
            log.error("Exception on findByName publisherName={}, message={}", publisherName, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @PostMapping()
    public ResponseEntity<PublisherDTO> create(@RequestBody Publisher publisher) throws Exception {
        try {
           return new ResponseEntity<>(publisherService.create(publisher), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on create publisher={}, type=DataIntegrityViolationException, message={}", publisher, e.getMessage());
            String codMessage =
                    Objects.requireNonNull(e.getMessage()).contains("ConstraintViolationException") ?
                    "PUBLISHER_CNPJ_DUPLICATED" :
                    "DATA_INTEGRITY_VIOLATION";
            throw new DataValidationException(MessageUtil.getMessage(codMessage));
        } catch (Exception e) {
            log.error("Exception on create publisher={}, message={}", publisher, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @PutMapping(UPDATE_PATH)
    public ResponseEntity<PublisherDTO> update(@PathVariable(value = "id") Long publisherId, @RequestBody PublisherDTO publisherDTO) throws InvalidRequestException, NotFoundException, DataValidationException {
        try {
            return ResponseEntity.ok(publisherService.edit(publisherId, publisherDTO));
        } catch (DataIntegrityViolationException e) {
            log.error("Exception on create publisher={}, type=DataIntegrityViolationException, message={}", publisherDTO, e.getMessage());
            String codMessage =
                    Objects.requireNonNull(e.getMessage()).contains("ConstraintViolationException") ?
                            "PUBLISHER_CNPJ_DUPLICATED" :
                            "DATA_INTEGRITY_VIOLATION";
            throw new DataValidationException(MessageUtil.getMessage(codMessage));
        } catch (Exception e) {
            log.error("Exception on update publisherId={}, message={}", publisherId, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }

    @DeleteMapping(SAFE_DELETE_PATH)
    public List<String> safeDelete(@PathVariable(value = "id") Long publisherId) throws NotFoundException {
        try {
            return publisherService.safeDelete(publisherId);
        } catch (Exception e) {
            log.error("Exception on safeDelete publisherId={}, message={}", publisherId, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }
    @DeleteMapping(DELETE_PATH)
    public Map<String, Long> delete(@PathVariable(value = "id") Long publisherId) throws LibraryStoreBooksException, NotFoundException {
        try {
            publisherService.delete(publisherId);
            Map<String, Long> response = new HashMap<>();
            response.put("id", publisherId);
            return response;
        } catch (Exception e) {
            log.error("Exception on delete publisherId={}, message={}", publisherId, e.getMessage());
            e.setStackTrace(new StackTraceElement[0]);
            throw e;
        }
    }
}
