import akka.actor.{Props, ActorSystem, Actor}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
/**
  * Created by knodus on 15/3/16.
  */


class FirstActor2 extends Actor{

  def receive={
    case "Good Morning" => sender ! "Hey"
    case "Its Terrible" => sender ! "Bye"
  }
}


object BadShakespeareanMain{

  val system=ActorSystem("BadShakesperan1")
  val actor= system.actorOf(Props[FirstActor2],"Greetings1")

  implicit val timeout = Timeout(10 seconds)
  def send(msg:String): Unit ={
    println(s"Me : $msg")
    val res:Future[String]= (actor ? msg ).mapTo[String]         // Ask
    println(Await.result(res,10 seconds))

  }

  //Driver

  def main(args: Array[String]): Unit ={
    send("Good Morning")
    send("Its Terrible")
    system.shutdown()
  }
}
