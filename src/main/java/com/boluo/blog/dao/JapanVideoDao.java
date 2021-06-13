package com.boluo.blog.dao;

import com.boluo.blog.domain.JapanMovie;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JapanVideoDao {

    void insertMovie(@Param("jm") JapanMovie jm);

    void insertMovies(@Param("list") List<JapanMovie> insertMovieList);

}
