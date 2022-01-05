
/* A Bison parser, made by GNU Bison 2.4.1.  */

/* Skeleton interface for Bison's Yacc-like parsers in C
   
      Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */


/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     INT = 258,
     FLOAT = 259,
     LONG = 260,
     UNSIGNED = 261,
     STRING = 262,
     CHAR = 263,
     WHILE = 264,
     IF = 265,
     ELSE = 266,
     READ = 267,
     PRINT = 268,
     plus = 269,
     minus = 270,
     mul = 271,
     division = 272,
     mod = 273,
     eq = 274,
     equal = 275,
     different = 276,
     less = 277,
     more = 278,
     lessOrEqual = 279,
     moreOrEqual = 280,
     leftRoundBracket = 281,
     rightRoundBracket = 282,
     semicolon = 283,
     leftCurlyBracket = 284,
     rightCurlyBracket = 285,
     IDENTIFIER = 286,
     NUMBER_CONST = 287,
     STRING_CONST = 288,
     CHAR_CONST = 289
   };
#endif
/* Tokens.  */
#define INT 258
#define FLOAT 259
#define LONG 260
#define UNSIGNED 261
#define STRING 262
#define CHAR 263
#define WHILE 264
#define IF 265
#define ELSE 266
#define READ 267
#define PRINT 268
#define plus 269
#define minus 270
#define mul 271
#define division 272
#define mod 273
#define eq 274
#define equal 275
#define different 276
#define less 277
#define more 278
#define lessOrEqual 279
#define moreOrEqual 280
#define leftRoundBracket 281
#define rightRoundBracket 282
#define semicolon 283
#define leftCurlyBracket 284
#define rightCurlyBracket 285
#define IDENTIFIER 286
#define NUMBER_CONST 287
#define STRING_CONST 288
#define CHAR_CONST 289




#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif

extern YYSTYPE yylval;


