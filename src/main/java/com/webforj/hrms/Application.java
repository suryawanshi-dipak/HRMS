package com.webforj.hrms;

import com.webforj.App;
import com.webforj.annotation.AppProfile;
import com.webforj.annotation.JavaScript;
import com.webforj.annotation.Routify;
import com.webforj.annotation.StyleSheet;

@Routify(packages = "com.webforj.hrms.views")
@StyleSheet("ws://app.css")
// @JavaScript("ws://app.js")
@AppProfile(name = "HRMS", shortName = "HRMS")
public class Application extends App {}
