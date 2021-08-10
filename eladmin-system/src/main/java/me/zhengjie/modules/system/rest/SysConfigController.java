/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package me.zhengjie.modules.system.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.SysConfig;
import me.zhengjie.modules.system.service.SysConfigService;
import me.zhengjie.modules.system.service.dto.SysConfigQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ron
 * @website https://el-admin.vip
 * @date 2021-07-28
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "系統參數管理")
@RequestMapping("/api/sysConfig")
public class SysConfigController {

    private final SysConfigService sysConfigService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('sysConfig:list')")
    public void download(HttpServletResponse response, SysConfigQueryCriteria criteria) throws IOException {
        sysConfigService.download(sysConfigService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询系統參數")
    @ApiOperation("查询系統參數")
    @PreAuthorize("@el.check('sysConfig:list')")
    public ResponseEntity<Object> query(SysConfigQueryCriteria criteria,@PageableDefault(sort = {"type"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(sysConfigService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增系統參數")
    @ApiOperation("新增系統參數")
    @PreAuthorize("@el.check('sysConfig:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody SysConfig resources) {
        return new ResponseEntity<>(sysConfigService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改系統參數")
    @ApiOperation("修改系統參數")
    @PreAuthorize("@el.check('sysConfig:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody SysConfig resources) {
        sysConfigService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除系統參數")
    @ApiOperation("删除系統參數")
    @PreAuthorize("@el.check('sysConfig:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        sysConfigService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}