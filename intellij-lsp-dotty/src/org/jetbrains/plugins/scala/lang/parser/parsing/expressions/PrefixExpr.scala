package org.jetbrains.plugins.scala.lang.parser.parsing.expressions

import org.jetbrains.plugins.scala.lang.parser.ScalaElementTypes
import org.jetbrains.plugins.scala.lang.parser.parsing.builder.ScalaPsiBuilder

/** 
* @author Alexander Podkhalyuzin
* Date: 03.03.2008
*/

/*
 * PrefixExpr ::= ['-' | '+' | '~' | '!'] SimpleExpr
 */
object PrefixExpr extends PrefixExpr {
  override protected def simpleExpr = SimpleExpr
}

trait PrefixExpr {
  protected def simpleExpr: SimpleExpr

  def parse(builder: ScalaPsiBuilder): Boolean = {
    builder.getTokenText match {
      case "-" | "+" | "~" | "!" =>
        val prefixMarker = builder.mark
        val refExpr = builder.mark
        builder.advanceLexer()
        refExpr.done(ScalaElementTypes.REFERENCE_EXPRESSION)
        if (!simpleExpr.parse(builder)) {
          prefixMarker.rollbackTo(); false
        } else {
          prefixMarker.done(ScalaElementTypes.PREFIX_EXPR); true
        }
      case _ => simpleExpr.parse(builder)
    }
  }
}