package org.jetbrains.plugins.scala.lang.psi.impl.statements

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElementVisitor
import org.jetbrains.plugins.scala.extensions.ifReadAllowed
import org.jetbrains.plugins.scala.lang.parser.ScalaElementTypes
import org.jetbrains.plugins.scala.lang.psi.ScalaStubBasedElementImpl
import org.jetbrains.plugins.scala.lang.psi.api.ScalaElementVisitor
import org.jetbrains.plugins.scala.lang.psi.api.base._
import org.jetbrains.plugins.scala.lang.psi.api.base.types.ScTypeElement
import org.jetbrains.plugins.scala.lang.psi.api.statements._
import org.jetbrains.plugins.scala.lang.psi.stubs.ScVariableStub
import org.jetbrains.plugins.scala.lang.psi.types.result.TypeResult


/**
 * @author Alexander Podkhalyuzin
 */

class ScVariableDeclarationImpl private (stub: ScVariableStub, node: ASTNode)
  extends ScalaStubBasedElementImpl(stub, ScalaElementTypes.VARIABLE_DECLARATION, node) with ScVariableDeclaration {

  def this(node: ASTNode) = this(null, node)

  def this(stub: ScVariableStub) = this(stub, null)

  override def toString: String = "ScVariableDeclaration: " + ifReadAllowed(declaredNames.mkString(", "))("")

  def `type`(): TypeResult = this.flatMapType(typeElement)

  def declaredElements: Seq[ScFieldId] = getIdList.fieldIds

  def typeElement: Option[ScTypeElement] = byPsiOrStub(findChild(classOf[ScTypeElement]))(_.typeElement)

  def getIdList: ScIdList = getStubOrPsiChild(ScalaElementTypes.IDENTIFIER_LIST)

  override def accept(visitor: ScalaElementVisitor) {
    visitor.visitVariableDeclaration(this)
  }

  override def accept(visitor: PsiElementVisitor) {
    visitor match {
      case s: ScalaElementVisitor => s.visitVariableDeclaration(this)
      case _ => super.accept(visitor)
    }
  }
}