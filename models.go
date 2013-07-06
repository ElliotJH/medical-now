package main

import (
	"time"
)


type Patient struct {
	Id int64
	Name string
	Nurse_Id int64
}
type Nurse struct {
	Id int64
	Name string
	Password string
	Key string
	Device_Id int64
}
type Scheduled_Procedure struct {
	Id int64
	First_Occasion time.Time
	Period time.Duration
	Patient_Id int64
	Procedure_Template_Id int64
}
type Procedure_Template struct {
	Id int64
	Name string
	Description string
	URI string
}
type Procedure struct {
	Id int64
	Scheduled_Procedure_Id int64
}

type Device struct {
	Id int64
	Ios bool
	DeviceToken string
}
