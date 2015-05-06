package me.lightspeed7.thinking.functional

import org.scalatest.FunSuite
import org.scalatest.Matchers._
import java.util.ArrayList

class ExamplesTest extends FunSuite {

  // Task - given a list of numbers spli them into two lists of even and odd numbers
  val fibs = Seq(0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811)

  test("Initial test") {
    println(s"Fibs(${fibs.length}) = ${fibs}")
  }

  test("imperative style") {
    val evens = new ArrayList[Integer]()
    val odds = new ArrayList[Integer]()
    for (i <- 0 until fibs.length) {
      val cur = fibs(i)
      if (cur % 2 == 0) evens.add(cur)
      else odds.add(cur)
    }
    evens.size should be(10)
    odds.size should be(19)
    println(s"Evens(${evens.size}) - ${evens}")
    println(s"Odds(${odds.size})   - ${odds}")
  }

  test("removing side effects,  case match") {
    val evens = new ArrayList[Integer]()
    val odds = new ArrayList[Integer]()
    for (cur <- fibs) {
      (cur % 2) match {
        case 0 => evens.add(cur)
        case _ => odds.add(cur)
      }
    }

    evens.size should be(10)
    odds.size should be(19)
  }

  test("removing side effects,  immutable collections") {
    var evens = Seq[Int]()
    var odds = Seq[Int]()
    for (cur <- fibs) {
      (cur % 2) match {
        case 0 => evens = evens :+ cur
        case _ => odds = odds :+ cur
      }
    }

    evens.size should be(10)
    odds.size should be(19)
  }

  test("removing side effects,  introduce foldLeft ") {

    val initial = (Seq[Int](), Seq[Int]())

    val (evens, odds) = fibs.foldLeft(initial) { (total, cur) =>

      val evens = total._1
      val odds = total._2

      (cur % 2) match {
        case 0 => (evens :+ cur, odds)
        case _ => (evens, odds :+ cur)
      }
    }

    evens.size should be(10)
    odds.size should be(19)
  }

  test("functional - no side effects") {
    val initial = (Seq[Int](), Seq[Int]())
    val (evens, odds) = fibs.foldLeft(initial) { (total, cur) =>
      (cur % 2) match {
        case 0 => (total._1 :+ cur, total._2)
        case _ => (total._1, total._2 :+ cur)
      }
    }
    evens.size should be(10)
    odds.size should be(19)
    println(s"Evens(${evens.size}) - ${evens}")
    println(s"Odds(${odds.size})   - ${odds}")
  }

}
