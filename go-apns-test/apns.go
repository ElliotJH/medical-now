package main

import (
	"fmt"
	"github.com/virushuo/Go-Apns"
	// "os"
	"time"
)

func main() {
	fmt.Print("Starting\n")
	apn, err := apns.New("dev_cert.pem", "dev_key.pem", "gateway.sandbox.push.apple.com:2195", 1*time.Second)
	if err != nil {
		fmt.Printf("connect error: %s\n", err.Error())
		// os.Exit(1)
	}

	fmt.Print("Created APNS object\n")
	go readError(apn.ErrorChan)

	payload := apns.Payload{}
	payload.Aps.Alert.Body = "Body of text here."

	notification := apns.Notification{}
	notification.DeviceToken = "acd6d1a64004a4fc89a9cd449683613e297e2442490db770644a955d18026abc"
	notification.Identifier = 123456789
	notification.Payload = &payload

	fmt.Print("Sending Notification\n")
	err = apn.Send(&notification)
	if err != nil {
		fmt.Printf("connect error: %s\n", err.Error())
		// os.Exit(1)
	}

	apn.Close()

	fmt.Print("Sent\n")
}

func readError(errorChan <-chan error) {
	for {
		apnerror := <-errorChan
		fmt.Println(apnerror.Error())
	}
}
