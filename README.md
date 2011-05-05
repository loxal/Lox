#summary Lox Project Overview
#labels Featured,Phase-Design,Phase-Requirements

*Remark:* This wiki page is referenced by the README file within the Lox project repository and therefore should be modified only via web interface.

= Lox Project =

Lox is a W2E (Web Workspace Environment) application which is currently just a *concept and technology preview*. It is intended for demo purposes and not for any specific tasks yet. This BWE class web app, a Browser Workspace Environment application brings the usability & ergonomics of classic desktop apps to the web. Therefore this app is about implementing a generic state-of-the-art UI that can be extended with plugins for specific purposes.

  * neither JPA nor JDO is used for persitence
    * instead a custom persistence mechanism is used that *leverages the _performance_ of GAE's low-level datastore API*

== Try it! ==
The project ist completely mavenized: Just pull the sources and do a *mvn gwt:run* to get the project up and running!

== Milestones == 
  * *Milestone (M2)* MapReduce Service (MapReduce proof-of-concept has been already implemented)
  * *Milestone (M3)* Text Processing Service (using MapReduce)
  * *Milestone (M4)* Gadget-encapsulated Image Viewer (and Shop...)