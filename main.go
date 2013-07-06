package main

import("time")

func main() {
	patient := new(Patient)
	waitChan := make(chan *Procedure)
	addChan := make(chan *ScheduledProcedure)
	go patient.eventLoop(waitChan, addChan)
	addChan <-new(ScheduledProcedure)
	time.Sleep(1000 * time.Millisecond)
	addChan <-new(ScheduledProcedure)
	time.Sleep(1000 * time.Millisecond)

	for {
		<- waitChan
		print("Got procedure")
	}
}

func (p *Patient) eventLoop(dispatch chan *Procedure, additem chan *ScheduledProcedure) {
	responder := make(chan *Procedure)
	for {
		var scheduledprocedure *ScheduledProcedure
		var procedure *Procedure
		select {
		case procedure = <-responder:
			dispatch <- procedure
		case scheduledprocedure = <-additem:
			go scheduledprocedure.dispatchWhenReady(responder)
		}
	}
}

func (s *ScheduledProcedure) dispatchWhenReady(dispatch chan *Procedure) {
	time.Sleep(5000 * time.Millisecond)
	dispatch <- new(Procedure)
}
