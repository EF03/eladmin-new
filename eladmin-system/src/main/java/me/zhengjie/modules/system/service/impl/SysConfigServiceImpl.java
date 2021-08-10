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
package me.zhengjie.modules.system.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.SysConfig;
import me.zhengjie.modules.system.repository.SysConfigRepository;
import me.zhengjie.modules.system.service.SysConfigService;
import me.zhengjie.modules.system.service.dto.SysConfigDto;
import me.zhengjie.modules.system.service.dto.SysConfigQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.SysConfigMapper;
import me.zhengjie.utils.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ron
 * @website https://el-admin.vip
 * @description 服务实现
 * @date 2021-07-28
 **/
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl implements SysConfigService {

    private final SysConfigRepository sysConfigRepository;
    private final SysConfigMapper sysConfigMapper;

    @Override
    public Map<String, Object> queryAll(SysConfigQueryCriteria criteria, Pageable pageable) {
        Page<SysConfig> page = sysConfigRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(sysConfigMapper::toDto));
    }

    @Override
    public List<SysConfigDto> queryAll(SysConfigQueryCriteria criteria) {
        return sysConfigMapper.toDto(sysConfigRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysConfigDto findById(Long configId) {
        SysConfig sysConfig = sysConfigRepository.findById(configId).orElseGet(SysConfig::new);
        ValidationUtil.isNull(sysConfig.getConfigId(), "SysConfig", "configId", configId);
        return sysConfigMapper.toDto(sysConfig);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysConfigDto findByType(String type) {
        SysConfig sysConfig = sysConfigRepository.findByType(type).orElseGet(SysConfig::new);
        ValidationUtil.isNull(sysConfig.getType(), "SysConfig", "type", type);
        return sysConfigMapper.toDto(sysConfig);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysConfigDto create(SysConfig resources) {
        if (sysConfigRepository.existsByType(resources.getType())) {
            throw new BadRequestException("類型重複");
        }
        return sysConfigMapper.toDto(sysConfigRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysConfig resources) {
        SysConfig sysConfig = sysConfigRepository.findById(resources.getConfigId()).orElseGet(SysConfig::new);
        ValidationUtil.isNull(sysConfig.getConfigId(), "SysConfig", "id", resources.getConfigId());
        if (!StringUtils.equalsAnyIgnoreCase(sysConfig.getType(), resources.getType()) && sysConfigRepository.existsByType(resources.getType())) {
            throw new BadRequestException("類型重複");
        }
        sysConfig.copy(resources);
        sysConfigRepository.save(sysConfig);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long configId : ids) {
            sysConfigRepository.deleteById(configId);
        }
    }

    @Override
    public void download(List<SysConfigDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SysConfigDto sysConfig : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("類型", sysConfig.getType());
            map.put("參數值", sysConfig.getValue());
            map.put("描述", sysConfig.getDesc());
            map.put("創建人", sysConfig.getCreateBy());
            map.put("更新人", sysConfig.getUpdateBy());
            map.put("創建時間", sysConfig.getCreateTime());
            map.put("更新時間", sysConfig.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}