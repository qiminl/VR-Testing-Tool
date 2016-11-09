package main

//when updating adunit or apps, id field will be ignored.
//when 

import (

	"bytes"
	"io/ioutil"
	"fmt"
	"net/http"
    
    "io"
    "os"
	
)

var(

    //general
    //dev
    //URL_BASE                    = "http://dev.desk.vradx.com/api/v1/"
    //appId                       = "33"
    //pubId                       = "2"
    
    //production
    URL_BASE                    = "http://desk.3dadx.com/api/v1/"
    pubId                       = "108"
    appId                       = "161" //App0911_288 
    
    USERID                      = "/" + pubId //pub0911, 2YICMnrTCxPJxy

    TIME_FROME                  = "Y-m-d H:i"
    TIME_TO                     = "Y-m-d H:i"
    SORT                        = "TBD"
    OFFSET                      = "0"
    limit                       = "1"

    APPID_DELETE                = "/233"
    APPID                       = "/"+appId
    
    ADUNITID                    = "/52"

    //user
	USERS                       = "users"
	APPS                        = "apps"
	USERS_ADUNITS               = "adunits"
    PUBLISHERS                  = "publishers"
    //DEV_VRAD_TOKEN              = "?token=2y13DBqLrOdUXjz4vUOye7EhelLw9BhF" //dev.desk
    DEV_VRAD_TOKEN              = "?token=2y13jthJDqJcCkl2NQAer86Nsev7aMty" //desk.3dadx
    //apps
    APPS_PARA                   = "&offset=1&limit=1&publisherId=1&platform=1&type=1"
    ICO                         = "/ico"
    ICO_URL                     = "/url"
    
    //reports
    REPORTS                     = "reports"
    REPORTS_PUBLISHERAPPS       = "reports/publisherapps"
    REPORTS_PUBLISHERADSLOTS    = "reports/publisheradslots"
    REPORTS_pubId               = "&publisherId=" + pubId
    REPORTS_appId               = "&appId=" + appId
    REPORTS_ADMINPUBLISHERS     = "reports/adminpublishers"

    ///adunits
    ADUNITS                     = "adunits"

    //POST variables
    users_body          = []byte(`{
            "id": 224,
            "login": "admin_vrad",
            "password": "13faf",
            "email": "asdf",
            "name": "asdf",
            "lang": "asdf",
            "timezaone": "asdf",
            "created_at": "adsf",
            "updated_at": "asdf"
    }`)

    ICO_BODY            = []byte(`{
        "ico": "https://cdn.arstechnica.net/wp-content/uploads/2016/02/5718897981_10faa45ac3_b-640x624.jpg"
    }`)


    APPS_CREATE_2       = []byte(`{
      "publisher_id": 108,
      "platform": 2, 
      "name": "ATEA",
      "description": "ASDF",
      
      "url": "http://en.etoron.com",
      "type": 1,
      "adUnits": [
        {
          "name": "1",
          "ad_type":1,
          "width": 60,
          "height": 60,
          "default_image_url": "http://www.gg.com",
          "format":0,
          "editCpmPrice": 1,
          "editCpcPrice": 1,
          "editCpaPrice": 1
        }
      ]
    }`)

    adunits_create_body = []byte(`{
      "id":2,
      "app_id": 161,
      "name": "test",
      "format": 0,
      "adType": [
        1
      ],
      "editCpmPrice": 3,
      "editCpcPrice": 2,
      "editCpaPrice": 1
    }`)

    PB_UPDATE_BODY      = []byte(`{ 
        "email": "tttttt@gmail.com" 
    }`)

    APPS_UPDATE_BODY    =[]byte(`{ 
        "id":33, 
        "publisher_id":108, 
        "name":"123", 
        "platform":2, 
        "description":"123" 
    }`)

    PUBLISHERS_CREATE_BODY = []byte(`{
      "login": "pub_test0911",
      "email": "haha@gmail.com",
      "name": "pub_test",
      "phone": "1233121233",
      "company": "pub_test",
      "cpm_fee":1,
      "revshare":1
    }`)
)

func main() {
    // res, _ := http.Post("http://dev.desk.vradx.com/api/v1/adunits?token=2y13DBqLrOdUXjz4vUOye7EhelLw9BhF", "application/json; charset=utf-8", bytes.NewBuffer(adunits_create_body))
    // io.Copy(os.Stdout, res.Body)
    // fmt.Println("Status: ", res.Status)
	//Get method
    fmt.Println("Getting ... ")
    // GET_PUBLISHER() // /publishers && /publishers/{userId}
    // GET_APPS() // /apps && /apps/apps/{appId}
    // GET_REPORTS()
    // GET_ADUNITS()
    fmt.Println("Finished GET!\nPosting...\n")

    // POST_PB()
    // POST_APPS()
    // POST_ADUNITS()

    fmt.Println("Finished Posting!\nDeleteting...\n")
    //TODO change apps id to be delete, use the one just created or some exsiting app. 
    DELET_APPS() 

    //updates:
    PUT_APPS()
    PUT_PB()
}

func GET_ADUNITS(){
    /*****************
     * adunits
     *****************/
    fmt.Println("\nGET adunits")
    // /adunits
    fmt.Println(ADUNITS + " result :")
    getR(URL_BASE + ADUNITS + DEV_VRAD_TOKEN)
    // /adunits/{adunitId}
    fmt.Println(ADUNITS+ ADUNITID + " result :")
    getR(URL_BASE + ADUNITS + ADUNITID+ DEV_VRAD_TOKEN)
}

func GET_PUBLISHER() {
    /*****************
    * publishers
    *****************/
    fmt.Println("\nGET Publishers")
    // /publishers
    fmt.Println(PUBLISHERS+ " result :")
    getR(URL_BASE + PUBLISHERS + DEV_VRAD_TOKEN)
    // /publishers/{userId}
    fmt.Println(PUBLISHERS + USERID + " result :")
    getR(URL_BASE + PUBLISHERS + USERID + DEV_VRAD_TOKEN)
}

func GET_APPS(){
    /*****************
     * APPS
     *****************/
    fmt.Println("\nGET Apps")
    // /apps
    fmt.Println(APPS + " result :")
    getR(URL_BASE + APPS + DEV_VRAD_TOKEN) //+ APPS_PARA)
    // /apps/{appId}
    fmt.Println(APPS + APPID + " result :")
    getR(URL_BASE + APPS + APPID + DEV_VRAD_TOKEN)
    // /apps/{appId}/ico
    fmt.Println(APPS + APPID + ICO + " result :")
    getR(URL_BASE + APPS + APPID + ICO + DEV_VRAD_TOKEN)
}

func GET_REPORTS(){
    // ****************
    //  * report
    //  ****************
    fmt.Println("\nGET report")
    // /reports/publisherapps
    fmt.Println(REPORTS_PUBLISHERAPPS + DEV_VRAD_TOKEN + REPORTS_pubId + " result :")
    getR(URL_BASE + REPORTS_PUBLISHERAPPS + DEV_VRAD_TOKEN + REPORTS_pubId)
    // /reports/publisheradslots
    fmt.Println(REPORTS_PUBLISHERAPPS + DEV_VRAD_TOKEN + REPORTS_pubId + REPORTS_appId+ " result :")
    getR(URL_BASE + REPORTS_PUBLISHERAPPS + DEV_VRAD_TOKEN + REPORTS_pubId + REPORTS_appId)
    // /reports/adminpublishers
    fmt.Println(REPORTS_ADMINPUBLISHERS + " result :")
    getR(URL_BASE + REPORTS_ADMINPUBLISHERS + DEV_VRAD_TOKEN)
}
//create new APPS
func POST_APPS(){
    fmt.Println("\nPOST Apps")
    fmt.Println(APPS + " POST result :")
    http_post(URL_BASE + APPS + DEV_VRAD_TOKEN, APPS_CREATE_2) 

    // /apps/{appId}/ico
    fmt.Println(APPS + APPID + ICO + " result :")
    http_post(URL_BASE + APPS + APPID + ICO + ICO_URL+ DEV_VRAD_TOKEN, ICO_BODY)
}

func POST_ADUNITS(){
    fmt.Println("\nPOST ADUNITS")
    fmt.Println(ADUNITS + " POST result :")
    http_post(URL_BASE + ADUNITS + DEV_VRAD_TOKEN, adunits_create_body)
}

func POST_PB(){
    fmt.Println("\nPOST Publishers")
    fmt.Println(ADUNITS + " POST result :")
    http_post(URL_BASE + PUBLISHERS + DEV_VRAD_TOKEN, PUBLISHERS_CREATE_BODY)
}

func DELET_APPS(){
    fmt.Println("\nDELETE Apps")
    fmt.Println(APPS + " POST result :")
    //http_R( URL_BASE + APPS + DEV_VRAD_TOKEN, APPS_CREATE_BODY) //+ APPS_PARA
    http_do("DELETE", URL_BASE + APPS + "/233"+DEV_VRAD_TOKEN, APPS_CREATE_2) 
}

//update apps
func PUT_APPS(){
    fmt.Println("\nPUT Apps")
    fmt.Println(APPS + " PUT result :")
    //http_R( URL_BASE + APPS + DEV_VRAD_TOKEN, APPS_CREATE_BODY) //+ APPS_PARA
    http_do("PUT", URL_BASE + APPS + APPID + DEV_VRAD_TOKEN, APPS_UPDATE_BODY) 
}

//update apps
func PUT_PB(){
    fmt.Println("\nPUT Publisher")
    fmt.Println(PUBLISHERS + " PUT result :")
    //http_R( URL_BASE + APPS + DEV_VRAD_TOKEN, APPS_CREATE_BODY) //+ APPS_PARA
    http_do("PUT", URL_BASE + PUBLISHERS + USERID + DEV_VRAD_TOKEN, PB_UPDATE_BODY) 
}

func getR(url string){

    fmt.Println("URL:>", url)
    var jsonStr = []byte(`{"p":224}`)

    //url := "http://adserver.vradx.com/sdk?p=41"
    //fmt.Println("URL:>", url)
    req, err := http.NewRequest("GET", url, bytes.NewBuffer(jsonStr))
    req.Header.Set("X-Padh-Accept-Language", "hehe")
    req.Header.Set("X-Padh-User-Agent","Mozilla/5.0")
    req.Header.Set("X-Padh-Forwarded-For", "183.62.217.88")

    client := &http.Client{}
    resp, err := client.Do(req)
    // fmt.Println("response Body again:")
    // io.Copy(os.Stdout, resp.Body)
    if err != nil {
        //panic(err)
        fmt.Println("err panic?")
    }
    defer resp.Body.Close()

    fmt.Println("response Status:", resp.Status)
    
    if resp.Status == "200"{ 
        fmt.Println ("200 PASS\n")
    } else if resp.Status == "403"{
        fmt.Println ("403 authen failed\n")
    } else if resp.Status == "404"{
        fmt.Println ("404 not found\n")
    } 
    //fmt.Println("response Headers:", resp.Header)
    //body, _ := ioutil.ReadAll(resp.Body)
    //fmt.Println("response Body:", string(body))
}

func http_post(url string, jsonStr []byte){
    fmt.Println("URL:>", url)
    res, _ := http.Post(url, "application/json; charset=utf-8", bytes.NewBuffer(jsonStr))

    fmt.Println("response Status:", res.Status)
    if res.Status == "200"{ 
        fmt.Println ("200 PASS\n")
    } else if res.Status == "403"{
        fmt.Println ("403 authen failed\n")
    } else if res.Status == "404"{
        fmt.Println ("404 not found\n")
    } 
    // fmt.Println("response Headers:", res.Header)

    body, _ := ioutil.ReadAll(res.Body)
    fmt.Println("response Body:", string(body))
}


func http_do(t string, url string, jsonStr []byte) {
    client := &http.Client{}

    request, err := http.NewRequest(t, url, bytes.NewBuffer(jsonStr))
    if t == "PUT"{
        request.Header.Add("Content-Type", "application/x-www-form-urlencoded")
    }
    // request.SetBasicAuth("admin", "admin")
    // request.ContentLength = 23
    fmt.Println("method:" + request.Method)
    response, err := client.Do(request)
    fmt.Println("response Body again:")
    io.Copy(os.Stdout, response.Body)
    if err != nil {
        fmt.Println(err)
    } else {
        defer response.Body.Close()
        contents, err := ioutil.ReadAll(response.Body)
        if err != nil {
            fmt.Println(err)
        }
        fmt.Println("The calculated length is:", len(string(contents)), "for the url:", url)
        fmt.Println("Status Body", response.Status)
        fmt.Println("Header Body", response.Header)
        
        if response.StatusCode == 200{
            fmt.Println ("200 PASS : " + "\n")
        } else if response.StatusCode == 403{
            fmt.Println ("403 authen failed: " + "\n")
        } else if response.StatusCode == 404{
            fmt.Println ("404 not found: " + "\n")
        } 
    }
}
