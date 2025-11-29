package com.sun.foot.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.foot.entity.Person;
import com.sun.foot.mapper.PersonMapper;
import com.sun.tools.config.ExportConfig;
import com.sun.tools.config.ExportResult;
import com.sun.tools.util.LargeDataExcelExporter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final PersonMapper personMapper;

    private final LargeDataExcelExporter exporter = new LargeDataExcelExporter();


    public PersonService(PersonMapper personMapper) {
        this.personMapper = personMapper;
    }

    public List<Person> findAll() {
        return personMapper.findAll();
    }

    public PageInfo<Person> findAll(Pageable pageable) {
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        List<Person> list = personMapper.findAll();
        return new PageInfo<>(list);
    }

    public ExportResult exportYourData() {

        // 1. 获取总数据量
        long totalCount = personMapper.getCount();

        // 2. 创建数据提供者 - 这里对接你的数据库查询
        LargeDataExcelExporter.DataProvider dataProvider = (startIndex, limit) -> {

            // 分页参数
            int pageNumber = (int) (startIndex / limit);
            Pageable pageable = PageRequest.of(pageNumber, limit);

            // 调用你现有的查询方法
            PageInfo<Person> pageInfo = findAll(pageable);
            // 转换为Map格式
            return pageInfo.getList().stream()
                    .map(entity -> {
                        Map<String, Object> row = new HashMap<>();
                        row.put("id", entity.getId());
                        row.put("name", entity.getName());
                        row.put("age", entity.getAge());
                        // ... 添加你需要导出的字段
                        return row;
                    })
                    .collect(Collectors.toList());
        };

        // 3. 配置导出
        ExportConfig config = ExportConfig.builder()
                .fileName("你的导出文件名")
                .filePath("exports/测试file.xlsx")
                .title("测试标题")
                .headers(Arrays.asList("ID", "名称", "年龄")) // 表头
                .fieldNames(Arrays.asList("id", "name", "age")) // 对应的字段名
                .batchSize(10000)
                .build();

        // 4. 执行导出
        return exporter.exportLargeData(dataProvider, totalCount, config);
    }
}
