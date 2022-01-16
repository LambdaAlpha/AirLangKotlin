package airacle.air.parser

import airacle.air.interpreter.AirValue
import airacle.air.lexer.AirToken

sealed interface AirSyntaxNode

object CommentNode : AirSyntaxNode

data class TokenNode<T : AirToken>(val token: T) : AirSyntaxNode

data class ValueNode<T : AirValue>(val value: T) : AirSyntaxNode
