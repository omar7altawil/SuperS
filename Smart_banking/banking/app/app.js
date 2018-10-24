'use strict';
// =================================================================================
// Firebase
// =================================================================================
var admin = require("firebase-admin");
var serviceAccount = require("/home/omar/Desktop/banking/firebase/smart-banking-d46e6-firebase-adminsdk-uvsrr-a0ff9aef00.json");
admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: "https://smart-banking-d46e6.firebaseio.com"
  });
var db = admin.database();
var ref = db.ref();


// =================================================================================
// App Configuration
// =================================================================================
const {App} = require('jovo-framework');

const config = {
    logging: false,
};

const app = new App(config);


// =================================================================================
// App Logic
// =================================================================================

app.setHandler({    
     'LAUNCH':async function() {
        var UserId=this.getUserId();
        var Users;
        Users=await ref.once("value").then((snapshot) =>{
            return snapshot.val();});
        var userid=[];
        for (var key in Users) {
                        userid.push(Users[key]["userid"])
        } 
        var index=userid.indexOf(UserId)
        if(index > -1)
            this.toIntent("LoginIntent")
        else 
            this.followUpState('NewUser').ask("you are not registered with the banking skill,to register please conntact: 0059915, whould you like to know more about banking skill?","whould you like to know more about banking skill?")
    },

            'NewUser': {
                    'AMAZON.YesIntent': function() {
                        this.tell("with banking skill you can get full controll over your bank activity using only your voice, for example you can ask me about,you balance, latest transaction , or even  send money")
                    },
    
                    'AMAZON.NoIntent': function() {
                        this.tell("ok bye bye,see you soon")
                    },
                    
    },

    'LoginIntent':async function() {
        var UserId=this.getUserId();
        var Users;
        Users=await ref.once("value").then((snapshot) =>{
            return snapshot.val();});
        var keys=[];
        var userid=[];
        var Name=[];
        var logged_in=[];
        for (var key in Users) {
                        keys.push(key)
                        userid.push(Users[key]["userid"])
                        Name.push(Users[key]["name"])
                        logged_in.push(Users[key]["logged_in"]) 
        } 
        var index=userid.indexOf(UserId)
        if(logged_in[index]==false){
            db.ref(keys[index]).update({auth:1})
            this.followUpState("enterpassword").ask("Welcome back Mr "+Name[index]+",Enter your password to login, tell me when you are done,I am going to close my eays now.","are you done yet?")
        }
        else
            this.ask("hi Mr "+Name[index]+" you are already logged in, tell me,how can i help you?","how can i help you?")
    },
            'enterpassword': {
                    'DoneIntent':async function() {
                        var UserId=this.getUserId();
                        var Users;
                        Users=await ref.once("value").then((snapshot) =>{
                            return snapshot.val();});
                        var keys=[];
                        var userid=[];
                        var Name=[];
                        var logged_in=[];
                        var auth=[];
                        for (var key in Users) {
                                        keys.push(key)
                                        userid.push(Users[key]["userid"])
                                        Name.push(Users[key]["name"])
                                        logged_in.push(Users[key]["logged_in"]) 
                                        auth.push(Users[key]["auth"]) 
                        } 
                        var index=userid.indexOf(UserId)
                        if(auth[index]==2){
                            db.ref(keys[index]).update({logged_in:true})
                            this.ask("you have been authorized to use the skill Mr "+Name[index]+", tell me,how can i help you?","how can i help you?")
                        }
                        else if(auth[index]==3)
                            this.tell("sorry you are not authorized because you have entered a wrong password, please try again later")
                        else if(auth[index]==4)
                            this.tell("sorry you are not authorized because you have not entered the password, please try again later")
                        else
                            this.tell("an error has occurred, please contact us")
                            db.ref(keys[index]).update({auth:0})
                        }
    },
    'MyblanceIsIntent':async function() {
        var UserId=this.getUserId();
        var Users;
        Users=await ref.once("value").then((snapshot) =>{
            return snapshot.val();});
        var keys=[];
        var userid=[];
        var Name=[];
        var logged_in=[];
        var balance=[];
        for (var key in Users) {
                        keys.push(key)
                        userid.push(Users[key]["userid"])
                        Name.push(Users[key]["name"])
                        logged_in.push(Users[key]["logged_in"]) 
                        balance.push(Users[key]["balance"]) 
        } 
        var index=userid.indexOf(UserId)
        if(logged_in[index]==true)
            this.ask("hi Mr "+Name[index]+", your balance right now is "+balance[index]+"$")
        else
            this.toIntent("LoginIntent")
    },
    'transactionIntent':async function() {
        var UserId=this.getUserId();
        var Users;
        Users=await ref.once("value").then((snapshot) =>{
            return snapshot.val();});
        var keys=[];
        var userid=[];
        var logged_in=[];
        var transaction=[];
        for (var key in Users) {
                        keys.push(key)
                        userid.push(Users[key]["userid"])
                        logged_in.push(Users[key]["logged_in"]) 
                        transaction.push(Users[key]["transaction"]) 
        } 
        var index=userid.indexOf(UserId)
        var latest=transaction[index].length-1;
        console.log(latest)
        if(logged_in[index]==true){
            this.followUpState("tran").ask("your latest transaction was "+ transaction[index][latest]+", do you want to see more?")
        }
        else
            this.toIntent("LoginIntent")
    },
                'tran': {
                    'AMAZON.YesIntent':async function() {
                        var UserId=this.getUserId();
                        var Users;
                        Users=await ref.once("value").then((snapshot) =>{
                            return snapshot.val();});
                        var keys=[];
                        var userid=[];
                        var transaction=[];
                        for (var key in Users) {
                                        keys.push(key)
                                        userid.push(Users[key]["userid"])
                                        transaction.push(Users[key]["transaction"]) 
                        } 
                        var tran=""
                        var index=userid.indexOf(UserId)
                        for(var key in transaction[index])
                            tran=tran+","+transaction[index][key]
                        this.ask("here is a list of all your transaction,"+tran)
                    },
                    'AMAZON.NoIntent': function() {
                        this.ask("yes sir")
                    },
    },

    'sendIntent':async function() {
        var UserId=this.getUserId();
        var Users;
        Users=await ref.once("value").then((snapshot) =>{
            return snapshot.val();});
        var keys=[];
        var userid=[];
        var logged_in=[];
        var transaction=[];
        for (var key in Users) {
                        keys.push(key)
                        userid.push(Users[key]["userid"])
                        logged_in.push(Users[key]["logged_in"]) 
                        transaction.push(Users[key]["transaction"]) 
        } 
        var index=userid.indexOf(UserId)
        var latest=transaction[index].length-1;
        console.log(latest)
        if(logged_in[index]==true){
            this.followUpState("tran").ask("your latest transaction was "+ transaction[index][latest]+", do you want to see more?")
        }
        else
            this.toIntent("LoginIntent")
    },

    'AMAZON.StopIntent': function() {
            this.tell('I hope to see you again soon.')
    },
    'AMAZON.HelpIntent': function() {
            this.ask('how can i help you?,you can ask me to check you balance, or to send money',"how can i help you?")
    },

});

module.exports.app = app;
