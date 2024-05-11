package com.splitshare.SignUpService.Repository;

import com.splitshare.SignUpService.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignUpRepo extends JpaRepository<UserModel,Long> {

}
