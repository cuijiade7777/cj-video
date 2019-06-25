package com.cj.mapper;

import java.util.List;

import com.cj.pojo.SearchRecords;
import com.cj.utils.MyMapper;

public interface SearchRecordsMapper extends MyMapper<SearchRecords> {

	List<String> getHotWords();
}