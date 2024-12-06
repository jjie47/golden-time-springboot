package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Duty;
import com.example.demo.entity.Like;

@Repository
public interface DutyRepository extends JpaRepository<Duty, String>{

}
