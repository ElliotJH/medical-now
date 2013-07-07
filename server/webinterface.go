package main

import (
	"net/http"
	"github.com/gorilla/mux"
	"code.google.com/p/go.crypto/bcrypt"
	"fmt"
	"crypto/md5"
	"time"
	"io"
)

func authHandler(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()
	data := r.Form
	user := data.Get("user")
	pass := data.Get("password")

	hashed_pass, err := dbmap.SelectStr("select Password from Nurse where Nurse.Name=?", user)
	if err != nil {
		panic(err)
	}
	
	valid_login := bcrypt.CompareHashAndPassword([]byte(hashed_pass), []byte(pass))

	if valid_login != nil {
		fmt.Fprintf(w, "Invalid Login")
	} else {
		key, err := dbmap.SelectStr("select Key from Nurse where Nurse.Name=?", user)
		if err != nil {
			panic(err);
		} else {
			if key == "" {
				key = genKey(user)
				dbmap.Exec("update Nurse set Key=? where Name=?", key, user)
				fmt.Fprintf(w, key)
			} else {
				fmt.Fprintf(w, key)
			}
			
		}
	}
}

func genKey(name string) string {
	h := md5.New()
	io.WriteString(h, name)
	io.WriteString(h, "b2jxi5S8")
	io.WriteString(h, time.Now().Format(time.UnixDate))
	key := fmt.Sprintf("%x", h.Sum([]byte{})) 
	return key
}

func createUserHandler(w http.ResponseWriter, r *http.Request) {
	r.ParseForm()
	data := r.Form
	user := data.Get("user")
	pass := data.Get("password")

	hashed_pass, err := bcrypt.GenerateFromPassword([]byte(pass), 4)
	new_nurse := &Nurse {0, user, string(hashed_pass), "", 0}

	err = dbmap.Insert(new_nurse)
	if err != nil {
		fmt.Fprintf(w, "Error")
	} else {
		fmt.Fprintf(w, "Success")
	}
}

func serve() {
	r := mux.NewRouter()
	r.HandleFunc("/createUser", createUserHandler)
	r.HandleFunc("/authenticate", authHandler)
	
	err := http.ListenAndServe("127.0.0.1:9000", r)
	if err != nil {
		print("HTTP Server Failed to Run")
		panic(err)
	}
}
