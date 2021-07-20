import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'dart:async';
import 'package:flutter/services.dart';

void main() => runApp(
    MaterialApp(
      home: new HomePage(),
    )
);


class HomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      appBar: new AppBar(
        title: const Text(
            'Platform Specific Codding',
          style: TextStyle(
            color: Colors.black
          ),
        ),
        centerTitle: true,
        elevation: 0,
        backgroundColor: Colors.white,
      ),
      body: new MyHomePage(),
    );
  }
}


class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);
  final String title;
  @override
  _MyHomePageState createState() => new _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  // Create a methodChannel named "flutter.native/helper"
  // and assigned in platform variable.
  // By this variable we can call Native Method.
  static const platform = const MethodChannel('flutter.native/helper');

  @override
  Widget build(BuildContext context) {
    return Material(
      child: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(8.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [

              CupertinoButton(
                color: Colors.blueAccent,
                child: Text("Start Native Activity"),
                onPressed: () async{
                  try {
                    //Invoke a method named "startNativeActivity"
                    //startNativeActivity is the name of a function located in
                    //MainActivity that can be call from here.
                    await platform.invokeMethod('startNativeActivity');
                  } on PlatformException catch (e) {
                    print("Failed to Invoke: '${e.message}'.");
                  }
                }
              ),

              SizedBox(height: 8,),
              CupertinoButton(
                  color: Colors.blueAccent,
                  child: Text("Start Native Activity With Arguments"),
                  onPressed: () async{
                    try {
                      //Creating a Map
                      var data = {
                        "name":"Md. Imam Hossain",
                        "age":"23y",
                        "gender":"Male",
                        "current_time":DateTime.now().toString()
                      };
                      //Invoke a method named "startNativeActivity"
                      //startNativeActivity is the name of a function located in
                      //MainActivity that can be call from here.
                      //Inside the invokeMethod, we will send data as a arguments
                      await platform.invokeMethod('startNativeActivityWithArgs', data);
                    } on PlatformException catch (e) {
                      print("Failed to Invoke: '${e.message}'.");
                    }
                  }
              ),

              SizedBox(height: 8,),
              CupertinoButton(
                  color: Colors.blueAccent,
                  child: Text("Show Notification From Native"),
                  onPressed: () async{
                    try {
                      //Invoke a method named "startNativeActivity"
                      //startNativeActivity is the name of a function located in
                      //MainActivity that can be call from here.
                      await platform.invokeMethod('showNotificationFromNative', {
                        "title": "Test Title",
                        "message": "This is a Simple Notification Thrown from Native"
                      });
                    } on PlatformException catch (e) {
                      print("Failed to Invoke: '${e.message}'.");
                    }
                  }
              ),

              SizedBox(height: 8,),

            ],
          ),
        ),
      ),
    );
  }
}