package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.YoutubeDTO;

@Mapper
public interface YoutubeMapper {

	List<YoutubeDTO> getYoutube(@Param("offset") int offset, @Param("size") int size);

	int getTotal();

}
