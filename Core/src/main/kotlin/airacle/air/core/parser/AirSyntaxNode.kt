package airacle.air.core.parser

import airacle.air.core.interpreter.AirValue
import airacle.air.core.lexer.AirToken

sealed interface AirSyntaxNode

object CommentNode : AirSyntaxNode

data class TokenNode<T : AirToken>(val token: T) : AirSyntaxNode

data class ValueNode<T : AirValue>(val value: T) : AirSyntaxNode
