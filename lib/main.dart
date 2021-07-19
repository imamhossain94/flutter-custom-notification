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
        title: const Text('Platform Specific Code'),
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
      child: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          children: [

            CupertinoButton(child: Text("Start Native Activity"), onPressed: () async{
              try {
                //Invoke a method named "startNativeActivity"
                //startNativeActivity is the name of a function located in
                //MainActivity that can be call from here.
                await platform.invokeMethod('startNativeActivity');
              } on PlatformException catch (e) {
                print("Failed to Invoke: '${e.message}'.");
              }
            }),


            
          ],
        ),
      ),
    );
  }
}