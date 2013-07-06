package main

import(
	"time"
	"database/sql"
	_ "github.com/mattn/go-sqlite3"
	"github.com/coopernurse/gorp"
)

func main() {
	db, err := sql.Open("sqlite3", "./medical-now.db")
	if err != nil {
		panic(err)
	}
	dbmap := &gorp.DbMap{Db: db, Dialect: gorp.SqliteDialect{}}


	dbmap.AddTable(Patient{}).SetKeys(true, "Id")
	dbmap.AddTable(Nurse{}).SetKeys(true, "Id")
	dbmap.AddTable(Scheduled_Procedure{}).SetKeys(true, "Id")
	dbmap.AddTable(Procedure_Template{}).SetKeys(true, "Id")
	dbmap.AddTable(Procedure{}).SetKeys(true, "Id")
	dbmap.AddTable(Device{}).SetKeys(true, "Id")
	err = dbmap.CreateTablesIfNotExists()
	if err != nil {
		panic(err)
	}

	var patients []*Patient
	
	_, err = dbmap.Select(&patients, "select * from Patient")
	if err != nil {
		panic(err)
	}
	
	dispatch := make(chan *Procedure)
	addChannels := make([]chan *Scheduled_Procedure, 0)
	
	for _,patient := range patients {
		addChannel := make(chan *Scheduled_Procedure)
		go patient.eventLoop(dispatch, addChannel)
		addChannels = append(addChannels, addChannel)
		
	}

	go dispatchProcedure(dispatch)
	go scheduleProcedures(dispatch)
}


func dispatchProcedure(incoming chan *Procedure) {
	//var p *Procedure
	for {
		<-incoming
		print("Got Procedure")
	}
}

func (p *Patient) eventLoop(dispatch chan *Procedure, additem chan *Scheduled_Procedure) {
	responder := make(chan *Procedure)
	for {
		var scheduledprocedure *Scheduled_Procedure
		var procedure *Procedure
		select {
		case procedure = <-responder:
			dispatch <- procedure
		case scheduledprocedure = <-additem:
			go scheduledprocedure.dispatchWhenReady(responder)
		}
	}
}

func (s *Scheduled_Procedure) dispatchWhenReady(dispatch chan *Procedure) {
	time.Sleep(5000 * time.Millisecond)
	dispatch <- new(Procedure)
}
