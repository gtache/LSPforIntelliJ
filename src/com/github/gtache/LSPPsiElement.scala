package com.github.gtache

import javax.swing.Icon

import com.intellij.lang.{ASTNode, Language}
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.{Key, TextRange}
import com.intellij.psi._
import com.intellij.psi.scope.PsiScopeProcessor
import com.intellij.psi.search.{GlobalSearchScope, SearchScope}
import com.intellij.util.IncorrectOperationException

case class LSPPsiElement(var name: String, project: Project, start: Int, end: Int) extends PsiNameIdentifierOwner {
  /**
    * Returns the project to which the PSI element belongs.
    *
    * @return the project instance.
    * @throws PsiInvalidElementAccessException
    * if this element is invalid
    */
  override def getProject: Project = project

  /**
    * Returns the language of the PSI element.
    *
    * @return the language instance.
    */
  override def getLanguage: Language = null

  /**
    * Returns the PSI manager for the project to which the PSI element belongs.
    *
    * @return the PSI manager instance.
    */
  override def getManager: PsiManager = null

  /**
    * Returns the array of children for the PSI element.
    * Important: In some implementations children are only composite elements, i.e. not a leaf elements
    *
    * @return the array of child elements.
    */
  override def getChildren: Array[PsiElement] = null

  /**
    * Returns the parent of the PSI element.
    *
    * @return the parent of the element, or null if the element has no parent.
    */
  override def getParent: PsiElement = null

  /**
    * Returns the first child of the PSI element.
    *
    * @return the first child, or null if the element has no children.
    */
  override def getFirstChild: PsiElement = null

  /**
    * Returns the last child of the PSI element.
    *
    * @return the last child, or null if the element has no children.
    */
  override def getLastChild: PsiElement = null

  /**
    * Returns the next sibling of the PSI element.
    *
    * @return the next sibling, or null if the node is the last in the list of siblings.
    */
  override def getNextSibling: PsiElement = null

  /**
    * Returns the previous sibling of the PSI element.
    *
    * @return the previous sibling, or null if the node is the first in the list of siblings.
    */
  override def getPrevSibling: PsiElement = null

  /**
    * Returns the file containing the PSI element.
    *
    * @return the file instance, or null if the PSI element is not contained in a file (for example,
    *         the element represents a package or directory).
    * @throws PsiInvalidElementAccessException
    * if this element is invalid
    */
  override def getContainingFile: PsiFile = null

  /**
    * Returns the text range in the document occupied by the PSI element.
    *
    * @return the text range.
    */
  override def getTextRange: TextRange = new TextRange(start, end)

  /**
    * Returns the text offset of the PSI element relative to its parent.
    *
    * @return the relative offset.
    */
  override def getStartOffsetInParent: Int = start

  /**
    * Returns the length of text of the PSI element.
    *
    * @return the text length.
    */
  override def getTextLength: Int = end - start

  /**
    * Finds a leaf PSI element at the specified offset from the start of the text range of this node.
    *
    * @param offset the relative offset for which the PSI element is requested.
    * @return the element at the offset, or null if none is found.
    */
  override def findElementAt(offset: Int): PsiElement = null

  /**
    * Finds a reference at the specified offset from the start of the text range of this node.
    *
    * @param offset the relative offset for which the reference is requested.
    * @return the reference at the offset, or null if none is found.
    */
  override def findReferenceAt(offset: Int): PsiReference = null

  /**
    * Returns the offset in the file to which the caret should be placed
    * when performing the navigation to the element. (For classes implementing
    * {@link PsiNamedElement}, this should return the offset in the file of the
    * name identifier.)
    *
    * @return the offset of the PSI element.
    */
  override def getTextOffset: Int = start

  /**
    * Returns the text of the PSI element as a character array.
    *
    * @return the element text as a character array.
    */
  override def textToCharArray: Array[Char] = name.toCharArray

  /**
    * Returns the PSI element which should be used as a navigation target
    * when navigation to this PSI element is requested. The method can either
    * return {@code this} or substitute a different element if this element
    * does not have an associated file and offset. (For example, if the source code
    * of a library is attached to a project, the navigation element for a compiled
    * library class is its source class.)
    *
    * @return the navigation target element.
    */
  override def getNavigationElement: PsiElement = this

  /**
    * Returns the PSI element which corresponds to this element and belongs to
    * either the project source path or class path. The method can either return
    * {@code this} or substitute a different element if this element does
    * not belong to the source path or class path. (For example, the original
    * element for a library source file is the corresponding compiled class file.)
    *
    * @return the original element.
    */
  override def getOriginalElement: PsiElement = null

  /**
    * Checks if the text of this PSI element is equal to the specified character sequence.
    *
    * @param text the character sequence to compare with.
    * @return true if the text is equal, false otherwise.
    */
  override def textMatches(text: CharSequence): Boolean = getText == text

  //Q: get rid of these methods?

  /**
    * Checks if the text of this PSI element is equal to the text of the specified PSI element.
    *
    * @param element the element to compare the text with.
    * @return true if the text is equal, false otherwise.
    */
  override def textMatches(element: PsiElement): Boolean = getText == element.getText

  /**
    * Returns the text of the PSI element.
    *
    * @return the element text.
    */
  override def getText: String = name

  /**
    * Checks if the text of this element contains the specified character.
    *
    * @param c the character to search for.
    * @return true if the character is found, false otherwise.
    */
  override def textContains(c: Char): Boolean = getText.contains(c)

  /**
    * Passes the element to the specified visitor.
    *
    * @param visitor the visitor to pass the element to.
    */
  override def accept(visitor: PsiElementVisitor): Unit = {
    visitor.visitElement(this)
  }

  /**
    * Passes the children of the element to the specified visitor.
    *
    * @param visitor the visitor to pass the children to.
    */
  override def acceptChildren(visitor: PsiElementVisitor): Unit = {}

  /**
    * Creates a copy of the file containing the PSI element and returns the corresponding
    * element in the created copy. Resolve operations performed on elements in the copy
    * of the file will resolve to elements in the copy, not in the original file.
    *
    * @return the element in the file copy corresponding to this element.
    */
  override def copy: PsiElement = null

  /**
    * Adds a child to this PSI element.
    *
    * @param element the child element to add.
    * @return the element which was actually added (either { @code element} or its copy).
    * @throws IncorrectOperationException if the modification is not supported or not possible for some reason.
    */
  @throws[IncorrectOperationException]
  override def add(element: PsiElement): PsiElement = throw new IncorrectOperationException()

  /**
    * Adds a child to this PSI element, before the specified anchor element.
    *
    * @param element the child element to add.
    * @param anchor  the anchor before which the child element is inserted (must be a child of this PSI element)
    * @return the element which was actually added (either { @code element} or its copy).
    * @throws IncorrectOperationException if the modification is not supported or not possible for some reason.
    */
  @throws[IncorrectOperationException]
  override def addBefore(element: PsiElement, anchor: PsiElement): PsiElement = throw new IncorrectOperationException()

  /**
    * Adds a child to this PSI element, after the specified anchor element.
    *
    * @param element the child element to add.
    * @param anchor  the anchor after which the child element is inserted (must be a child of this PSI element)
    * @return the element which was actually added (either { @code element} or its copy).
    * @throws IncorrectOperationException if the modification is not supported or not possible for some reason.
    */
  @throws[IncorrectOperationException]
  override def addAfter(element: PsiElement, anchor: PsiElement): PsiElement = throw new IncorrectOperationException()

  /**
    * Checks if it is possible to add the specified element as a child to this element,
    * and throws an exception if the add is not possible. Does not actually modify anything.
    *
    * @param element the child element to check the add possibility.
    * @throws IncorrectOperationException if the modification is not supported or not possible for some reason.
    * @deprecated not all PSI implementations implement this method correctly.
    */
  @throws[IncorrectOperationException]
  override def checkAdd(element: PsiElement): Unit = throw new IncorrectOperationException()

  /**
    * Adds a range of elements as children to this PSI element.
    *
    * @param first the first child element to add.
    * @param last  the last child element to add (must have the same parent as { @code first})
    * @return the first child element which was actually added (either { @code first} or its copy).
    * @throws IncorrectOperationException if the modification is not supported or not possible for some reason.
    */
  @throws[IncorrectOperationException]
  override def addRange(first: PsiElement, last: PsiElement): PsiElement = throw new IncorrectOperationException()

  /**
    * Adds a range of elements as children to this PSI element, before the specified anchor element.
    *
    * @param first  the first child element to add.
    * @param last   the last child element to add (must have the same parent as { @code first})
    * @param anchor the anchor before which the child element is inserted (must be a child of this PSI element)
    * @return the first child element which was actually added (either { @code first} or its copy).
    * @throws IncorrectOperationException if the modification is not supported or not possible for some reason.
    */
  @throws[IncorrectOperationException]
  override def addRangeBefore(first: PsiElement, last: PsiElement, anchor: PsiElement): PsiElement = throw new IncorrectOperationException()

  /**
    * Adds a range of elements as children to this PSI element, after the specified anchor element.
    *
    * @param first  the first child element to add.
    * @param last   the last child element to add (must have the same parent as { @code first})
    * @param anchor the anchor after which the child element is inserted (must be a child of this PSI element)
    * @return the first child element which was actually added (either { @code first} or its copy).
    * @throws IncorrectOperationException if the modification is not supported or not possible for some reason.
    */
  @throws[IncorrectOperationException]
  override def addRangeAfter(first: PsiElement, last: PsiElement, anchor: PsiElement): PsiElement = throw new IncorrectOperationException()

  /**
    * Deletes this PSI element from the tree.
    *
    * @throws IncorrectOperationException if the modification is not supported
    *                                     or not possible for some reason (for example, the file containing the element is read-only).
    */
  @throws[IncorrectOperationException]
  override def delete(): Unit = throw new IncorrectOperationException()

  /**
    * Checks if it is possible to delete the specified element from the tree,
    * and throws an exception if the add is not possible. Does not actually modify anything.
    *
    * @throws IncorrectOperationException if the modification is not supported or not possible for some reason.
    * @deprecated not all PSI implementations implement this method correctly.
    */
  @throws[IncorrectOperationException]
  override def checkDelete(): Unit = throw new IncorrectOperationException()

  /**
    * Deletes a range of children of this PSI element from the tree.
    *
    * @param first the first child to delete (must be a child of this PSI element)
    * @param last  the last child to delete (must be a child of this PSI element)
    * @throws IncorrectOperationException if the modification is not supported or not possible for some reason.
    */
  @throws[IncorrectOperationException]
  override def deleteChildRange(first: PsiElement, last: PsiElement): Unit = throw new IncorrectOperationException()

  /**
    * Replaces this PSI element (along with all its children) with another element
    * (along with the children).
    *
    * @param newElement the element to replace this element with.
    * @return the element which was actually inserted in the tree (either { @code newElement} or its copy)
    * @throws IncorrectOperationException if the modification is not supported or not possible for some reason.
    */
  @throws[IncorrectOperationException]
  override def replace(newElement: PsiElement): PsiElement = throw new IncorrectOperationException()

  /**
    * Checks if this PSI element is valid. Valid elements and their hierarchy members
    * can be accessed for reading and writing. Valid elements can still correspond to
    * underlying documents whose text is different, when those documents have been changed
    * and not yet committed ({@link PsiDocumentManager#commitDocument(com.intellij.openapi.editor.Document)}).
    * (In this case an attempt to change PSI will result in an exception).
    *
    * Any access to invalid elements results in {@link PsiInvalidElementAccessException}.
    *
    * Once invalid, elements can't become valid again.
    *
    * Elements become invalid in following cases:
    * <ul>
    * <li>They have been deleted via PSI operation ({@link #delete()})</li>
    * <li>They have been deleted as a result of an incremental reparse (document commit)</li>
    * <li>Their containing file has been changed externally, or renamed so that its PSI had to be rebuilt from scratch</li>
    * </ul>
    *
    * @return true if the element is valid, false otherwise.
    * @see com.intellij.psi.util.PsiUtilCore#ensureValid(PsiElement)
    */
  override def isValid: Boolean = true

  /**
    * Checks if the contents of the element can be modified (if it belongs to a
    * non-read-only source file.)
    *
    * @return true if the element can be modified, false otherwise.
    */
  override def isWritable: Boolean = true

  /**
    * Returns the reference from this PSI element to another PSI element (or elements), if one exists.
    * If the element has multiple associated references (see {@link #getReferences()}
    * for an example), returns the first associated reference.
    *
    * @return the reference instance, or null if the PSI element does not have any
    *         associated references.
    * @see com.intellij.psi.search.searches.ReferencesSearch
    */
  override def getReference: PsiReference = new LSPPsiReference(this)

  /**
    * Returns all references from this PSI element to other PSI elements. An element can
    * have multiple references when, for example, the element is a string literal containing
    * multiple sub-strings which are valid full-qualified class names. If an element
    * contains only one text fragment which acts as a reference but the reference has
    * multiple possible targets, {@link PsiPolyVariantReference} should be used instead
    * of returning multiple references.
    * <p/>
    * Actually, it's preferable to call {@link PsiReferenceService#getReferences} instead
    * as it allows adding references by plugins when the element implements {@link ContributedReferenceHost}.
    *
    * @return the array of references, or an empty array if the element has no associated
    *         references.
    * @see PsiReferenceService#getReferences
    * @see com.intellij.psi.search.searches.ReferencesSearch
    */
  override def getReferences: Array[PsiReference] = null

  /**
    * Returns a copyable user data object attached to this element.
    *
    * @param key the key for accessing the user data object.
    * @return the user data object, or null if no such object is found in the current element.
    * @see #putCopyableUserData(Key, Object)
    */
  override def getCopyableUserData[T](key: Key[T]): T = throw new Error

  /**
    * Attaches a copyable user data object to this element. Copyable user data objects are copied
    * when the PSI elements are copied.
    *
    * @param key   the key for accessing the user data object.
    * @param value the user data object to attach.
    * @see #getCopyableUserData(Key)
    */
  override def putCopyableUserData[T](key: Key[T], value: T): Unit = {}

  /**
    * Passes the declarations contained in this PSI element and its children
    * for processing to the specified scope processor.
    *
    * @param processor  the processor receiving the declarations.
    * @param lastParent the child of this element has been processed during the previous
    *                   step of the tree up walk (declarations under this element do not need
    *                   to be processed again)
    * @param place      the original element from which the tree up walk was initiated.
    * @return true if the declaration processing should continue or false if it should be stopped.
    */
  override def processDeclarations(processor: PsiScopeProcessor, state: ResolveState, lastParent: PsiElement, place: PsiElement): Boolean = false

  /**
    * Returns the element which should be used as the parent of this element in a tree up
    * walk during a resolve operation. For most elements, this returns {@code getParent()},
    * but the context can be overridden for some elements like code fragments (see
    * {@link PsiElementFactory#createCodeBlockCodeFragment(String, PsiElement, boolean)}).
    *
    * @return the resolve context element.
    */
  override def getContext: PsiElement = null

  /**
    * Checks if an actual source or class file corresponds to the element. Non-physical elements include,
    * for example, PSI elements created for the watch expressions in the debugger.
    * Non-physical elements do not generate tree change events.
    * Also, {@link PsiDocumentManager#getDocument(PsiFile)} returns null for non-physical elements.
    * Not to be confused with {@link FileViewProvider#isPhysical()}.
    *
    * @return true if the element is physical, false otherwise.
    */
  override def isPhysical: Boolean = true

  /**
    * Returns the scope in which the declarations for the references in this PSI element are searched.
    *
    * @return the resolve scope instance.
    */
  override def getResolveScope: GlobalSearchScope = null

  /**
    * Returns the scope in which references to this element are searched.
    *
    * @return the search scope instance.
    * @see { @link com.intellij.psi.search.PsiSearchHelper#getUseScope(PsiElement)}
    */
  override def getUseScope: SearchScope = null

  /**
    * Returns the AST node corresponding to the element.
    *
    * @return the AST node instance.
    */
  override def getNode: ASTNode = null

  /**
    * toString() should never be presented to the user.
    */
  override def toString: String = "Name : " + name + " at offset " + start + " to " + end + " in " + project

  /**
    * This method shouldn't be called by clients directly, because there are no guarantees of it being symmetric.
    * It's called by {@link PsiManager#areElementsEquivalent(PsiElement, PsiElement)} internally, which clients should invoke instead.<p/>
    *
    * Implementations of this method should return {@code true} if the parameter is resolve-equivalent to {@code this}, i.e. it represents
    * the same entity from the language perspective. See also {@link PsiManager#areElementsEquivalent(PsiElement, PsiElement)} documentation.
    */
  override def isEquivalentTo(another: PsiElement): Boolean = this == another


  override def putUserData[T](key: Key[T], value: T): Unit = {}

  override def getUserData[T](key: Key[T]): T = throw new Error

  override def getIcon(flags: Int): Icon = null

  override def getNameIdentifier: PsiElement = this

  override def getName: String = name

  override def setName(name: String): PsiElement = {
    this.name = name
    this
  }
}