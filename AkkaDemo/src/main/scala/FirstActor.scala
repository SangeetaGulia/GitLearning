//import akka.actor.{Props, ActorSystem, Actor}
//
///**
//  * Created by knodus on 15/3/16.
//  */
//
//
//class FirstActor extends Actor{
//
//  def receive={
//    case "Good Morning" => println(" Actor saying GM")
//    case "Its Terrible" => println("Actor : Seriously :o ")
//  }
//}
//
//
//object BadShakespeareanMain{
//
//  val system=ActorSystem("BadShakesperan")
//  val actor= system.actorOf(Props[FirstActor],"Greetings")
//
//  def send(msg:String): Unit ={
//    println(s"Me : $msg")
//    actor ! msg                // Fire and Forget
//    Thread.sleep(100)
//  }
//
//  //Driver
//
//  def main(args: Array[String]): Unit ={
//    send("Good Morning")
//    send("Its Terrible")
//    system.shutdown()
//  }
//}
