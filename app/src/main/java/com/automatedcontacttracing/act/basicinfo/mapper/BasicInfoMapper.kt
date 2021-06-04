package com.automatedcontacttracing.act.basicinfo.mapper

import com.automatedcontacttracing.act.basicinfo.model.BasicInfo

object BasicInfoMapper {

    fun map(
        lastName: String,
        firstName: String,
        middleInitial: String,
        age: String,
        gender: String,
        emailAddress: String,
        contactNumber: String,
        address: String,
        temperature: String
    ): BasicInfo {
        return BasicInfo(
            lastName,
            firstName,
            middleInitial,
            age,
            gender,
            emailAddress,
            contactNumber,
            address,
            temperature
        )
    }
}