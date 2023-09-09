// $ANTLR 2.7.7 (20060906): "elijjah.g" -> "ElijjahParser.java"$

package tripleo.elijjah;

import antlr.*;
import antlr.collections.impl.BitSet;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.internal.Out;
import tripleo.elijah.contexts.*;
import tripleo.elijah.lang.builder.TypeAliasBuilder;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.lang.imports.AssigningImportStatement;
import tripleo.elijah.lang.imports.NormalImportStatement;
import tripleo.elijah.lang.imports.QualifiedImportStatement;
import tripleo.elijah.lang.imports.RootedImportStatement;
import tripleo.elijah.lang.types.OS_BuiltinType;
import tripleo.elijah.lang2.BuiltInTypes;

import java.util.ArrayList;
import java.util.List;

public class ElijjahParser extends antlr.LLkParser implements ElijjahTokenTypes {

	public static final String[] _tokenNames  = {
			"<0>",
			"EOF",
			"<2>",
			"NULL_TREE_LOOKAHEAD",
			"\"as\"",
			"\"cast_to\"",
			"\"package\"",
			"\"indexing\"",
			"IDENT",
			"TOK_COLON",
			"STRING_LITERAL",
			"CHAR_LITERAL",
			"NUM_INT",
			"NUM_FLOAT",
			"DOT",
			"\"extend\"",
			"\"class\"",
			"LBRACK",
			"RBRACK",
			"\"const\"",
			"\"struct\"",
			"\"signature\"",
			"\"interface\"",
			"\"abstract\"",
			"\"annotation\"",
			"\"exception\"",
			"LPAREN",
			"RPAREN",
			"LCURLY",
			"RCURLY",
			"\"type\"",
			"BECOMES",
			"BOR",
			"ANNOT",
			"\"namespace\"",
			"\"from\"",
			"\"import\"",
			"COMMA",
			"LT_",
			"\"constructor\"",
			"\"ctor\"",
			"\"destructor\"",
			"\"dtor\"",
			"\"continue\"",
			"\"break\"",
			"\"return\"",
			"\"with\"",
			"\"pre\"",
			"\"requires\"",
			"\"post\"",
			"\"ensures\"",
			"\"def\"",
			"\"immutable\"",
			"TOK_ARROW",
			"\"var\"",
			"\"val\"",
			"\"alias\"",
			"\"yield\"",
			"\"construct\"",
			"SEMI",
			"\"invariant\"",
			"\"access\"",
			"EQUAL",
			"PLUS_ASSIGN",
			"MINUS_ASSIGN",
			"STAR_ASSIGN",
			"DIV_ASSIGN",
			"MOD_ASSIGN",
			"SR_ASSIGN",
			"BSR_ASSIGN",
			"SL_ASSIGN",
			"BAND_ASSIGN",
			"BXOR_ASSIGN",
			"BOR_ASSIGN",
			"LOR",
			"LAND",
			"BXOR",
			"BAND",
			"NOT_EQUAL",
			"GT",
			"LE",
			"GE",
			"\"is_a\"",
			"SL",
			"SR",
			"BSR",
			"PLUS",
			"MINUS",
			"STAR",
			"DIV",
			"MOD",
			"INC",
			"DEC",
			"BNOT",
			"LNOT",
			"\"true\"",
			"\"false\"",
			"\"this\"",
			"\"null\"",
			"\"function\"",
			"\"procedure\"",
			"\"if\"",
			"\"else\"",
			"\"elseif\"",
			"\"match\"",
			"\"case\"",
			"\"while\"",
			"\"do\"",
			"\"iterate\"",
			"\"to\"",
			"\"generic\"",
			"QUESTION",
			"\"typeof\"",
			"\"func\"",
			"\"proc\"",
			"\"in\"",
			"\"out\"",
			"\"ref\"",
			"\"prop\"",
			"\"property\"",
			"\"get\"",
			"\"set\"",
			"WS",
			"SL_COMMENT",
			"ML_COMMENT",
			"ESC",
			"HEX_DIGIT",
			"VOCAB",
			"EXPONENT",
			"FLOAT_SUFFIX"
	};
	public static final BitSet   _tokenSet_0  = new BitSet(mk_tokenSet_0());
	public static final BitSet   _tokenSet_1  = new BitSet(mk_tokenSet_1());
	public static final BitSet   _tokenSet_10 = new BitSet(mk_tokenSet_10());
	public static final BitSet   _tokenSet_11 = new BitSet(mk_tokenSet_11());
	public static final BitSet   _tokenSet_12 = new BitSet(mk_tokenSet_12());
	public static final BitSet   _tokenSet_13 = new BitSet(mk_tokenSet_13());
	public static final BitSet   _tokenSet_14 = new BitSet(mk_tokenSet_14());
	public static final BitSet   _tokenSet_15 = new BitSet(mk_tokenSet_15());
	public static final BitSet   _tokenSet_16 = new BitSet(mk_tokenSet_16());
	public static final BitSet   _tokenSet_17 = new BitSet(mk_tokenSet_17());
	public static final BitSet   _tokenSet_18 = new BitSet(mk_tokenSet_18());
	public static final BitSet   _tokenSet_19 = new BitSet(mk_tokenSet_19());
	public static final BitSet   _tokenSet_2  = new BitSet(mk_tokenSet_2());
	public static final BitSet   _tokenSet_20 = new BitSet(mk_tokenSet_20());
	public static final BitSet   _tokenSet_21 = new BitSet(mk_tokenSet_21());
	public static final BitSet   _tokenSet_22 = new BitSet(mk_tokenSet_22());
	public static final BitSet   _tokenSet_23 = new BitSet(mk_tokenSet_23());
	public static final BitSet   _tokenSet_24 = new BitSet(mk_tokenSet_24());
	public static final BitSet   _tokenSet_25 = new BitSet(mk_tokenSet_25());
	public static final BitSet   _tokenSet_26 = new BitSet(mk_tokenSet_26());
	public static final BitSet   _tokenSet_27 = new BitSet(mk_tokenSet_27());
	public static final BitSet   _tokenSet_28 = new BitSet(mk_tokenSet_28());
	public static final BitSet   _tokenSet_29 = new BitSet(mk_tokenSet_29());
	public static final BitSet   _tokenSet_3  = new BitSet(mk_tokenSet_3());
	public static final BitSet   _tokenSet_30 = new BitSet(mk_tokenSet_30());
	public static final BitSet   _tokenSet_31 = new BitSet(mk_tokenSet_31());
	public static final BitSet   _tokenSet_32 = new BitSet(mk_tokenSet_32());
	public static final BitSet   _tokenSet_33 = new BitSet(mk_tokenSet_33());
	public static final BitSet   _tokenSet_34 = new BitSet(mk_tokenSet_34());
	public static final BitSet   _tokenSet_35 = new BitSet(mk_tokenSet_35());
	public static final BitSet   _tokenSet_36 = new BitSet(mk_tokenSet_36());
	public static final BitSet   _tokenSet_37 = new BitSet(mk_tokenSet_37());
	public static final BitSet   _tokenSet_38 = new BitSet(mk_tokenSet_38());
	public static final BitSet   _tokenSet_39 = new BitSet(mk_tokenSet_39());
	public static final BitSet   _tokenSet_4  = new BitSet(mk_tokenSet_4());
	public static final BitSet   _tokenSet_40 = new BitSet(mk_tokenSet_40());
	public static final BitSet   _tokenSet_41 = new BitSet(mk_tokenSet_41());
	public static final BitSet   _tokenSet_42 = new BitSet(mk_tokenSet_42());
	public static final BitSet   _tokenSet_43 = new BitSet(mk_tokenSet_43());
	public static final BitSet   _tokenSet_44 = new BitSet(mk_tokenSet_44());
	public static final BitSet   _tokenSet_45 = new BitSet(mk_tokenSet_45());
	public static final BitSet   _tokenSet_46 = new BitSet(mk_tokenSet_46());
	public static final BitSet   _tokenSet_47 = new BitSet(mk_tokenSet_47());
	public static final BitSet   _tokenSet_48 = new BitSet(mk_tokenSet_48());
	public static final BitSet   _tokenSet_49 = new BitSet(mk_tokenSet_49());
	public static final BitSet   _tokenSet_5  = new BitSet(mk_tokenSet_5());
	public static final BitSet   _tokenSet_50 = new BitSet(mk_tokenSet_50());
	public static final BitSet   _tokenSet_51 = new BitSet(mk_tokenSet_51());
	public static final BitSet   _tokenSet_52 = new BitSet(mk_tokenSet_52());
	public static final BitSet   _tokenSet_53 = new BitSet(mk_tokenSet_53());
	public static final BitSet   _tokenSet_54 = new BitSet(mk_tokenSet_54());
	public static final BitSet   _tokenSet_55 = new BitSet(mk_tokenSet_55());
	public static final BitSet   _tokenSet_56 = new BitSet(mk_tokenSet_56());
	public static final BitSet   _tokenSet_57 = new BitSet(mk_tokenSet_57());
	public static final BitSet   _tokenSet_58 = new BitSet(mk_tokenSet_58());
	public static final BitSet   _tokenSet_59 = new BitSet(mk_tokenSet_59());
	public static final BitSet   _tokenSet_6  = new BitSet(mk_tokenSet_6());
	public static final BitSet   _tokenSet_60 = new BitSet(mk_tokenSet_60());
	public static final BitSet   _tokenSet_61 = new BitSet(mk_tokenSet_61());
	public static final BitSet   _tokenSet_62 = new BitSet(mk_tokenSet_62());
	public static final BitSet   _tokenSet_63 = new BitSet(mk_tokenSet_63());
	public static final BitSet   _tokenSet_64 = new BitSet(mk_tokenSet_64());
	public static final BitSet   _tokenSet_65 = new BitSet(mk_tokenSet_65());
	public static final BitSet   _tokenSet_66 = new BitSet(mk_tokenSet_66());
	public static final BitSet   _tokenSet_67 = new BitSet(mk_tokenSet_67());
	public static final BitSet   _tokenSet_68 = new BitSet(mk_tokenSet_68());
	public static final BitSet   _tokenSet_69 = new BitSet(mk_tokenSet_69());
	public static final BitSet   _tokenSet_7  = new BitSet(mk_tokenSet_7());
	public static final BitSet   _tokenSet_70 = new BitSet(mk_tokenSet_70());
	public static final BitSet   _tokenSet_71 = new BitSet(mk_tokenSet_71());
	public static final BitSet   _tokenSet_72 = new BitSet(mk_tokenSet_72());
	public static final BitSet   _tokenSet_8  = new BitSet(mk_tokenSet_8());
	public static final BitSet   _tokenSet_9  = new BitSet(mk_tokenSet_9());

	private static final long[] mk_tokenSet_0() {
		long[] data = {2L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_1() {
		long[] data = {72057722887045186L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_10() {
		long[] data = {256L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_11() {
		long[] data = {671350784L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_12() {
		long[] data = {268959744L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_13() {
		long[] data = {403177472L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_14() {
		long[] data = {4503599695003904L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_15() {
		long[] data = {72057722887045120L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_16() {
		long[] data = {334561536L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_17() {
		long[] data = {536870912L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_18() {
		long[] data = {335691008L, 137317318656L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_19() {
		long[] data = {-4611685736234451664L, 137438953471L, 0L, 0L};
		return data;
	}

	public @Nullable Out out;

	private static final long[] mk_tokenSet_20() {
		long[] data = {4021714048290372864L, 54077555145179136L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_21() {
		long[] data = {3587125479998062848L, 54043195528445952L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_22() {
		long[] data = {2251825583587584L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_23() {
		long[] data = {4163586232301486336L, 54043195528445952L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_24() {
		long[] data = {4597752588120341760L, 54077555145179136L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_25() {
		long[] data = {3587117233660855552L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_26() {
		long[] data = {2434195728517136640L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_27() {
		long[] data = {4163586369740456258L, 54043195528445952L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_28() {
		long[] data = {4163586369740439874L, 54043195528445952L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_29() {
		long[] data = {137842130944L, 0L, 0L};
		return data;
	}

	@Nullable Context cur;

	private static final long[] mk_tokenSet_30() {
		long[] data = {4172593438334369536L, 54043332845764608L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_31() {
		long[] data = {486520701487660288L, 34359616733184L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_32() {
		long[] data = {-3548704282779058896L, 34359738367999L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_33() {
		long[] data = {1062981454327954688L, 34359616733184L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_34() {
		long[] data = {-4925812092436622L, 270286346386407423L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_35() {
		long[] data = {486388760092228864L, 34359616733184L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_36() {
		long[] data = {-3548704248419320528L, 69544110456831L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_37() {
		long[] data = {486520702024531200L, 34359616733184L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_38() {
		long[] data = {-4925812158496910L, 270286346386407423L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_39() {
		long[] data = {1064670304188218624L, 34359616733184L, 0L, 0L};
		return data;
	}

	@Nullable IExpression expr;

	private static final long[] mk_tokenSet_40() {
		long[] data = {137707388928L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_41() {
		long[] data = {486450332743482624L, 34359616733184L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_42() {
		long[] data = {-3548774651523236560L, 34359738367999L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_43() {
		long[] data = {1062911085583777024L, 34359616733184L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_44() {
		long[] data = {-3548774617097437902L, 69544110456831L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_45() {
		long[] data = {-3548774617163498192L, 69544110456831L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_46() {
		long[] data = {488139183149006080L, 34359616733184L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_47() {
		long[] data = {872561920L, 137317318656L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_48() {
		long[] data = {4602256187813772610L, 54077555145179136L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_49() {
		long[] data = {4163586232637177088L, 54043332845764608L, 0L, 0L};
		return data;
	}

	Scope3 sco;

	private static final long[] mk_tokenSet_50() {
		long[] data = {486415148371393792L, 34359616733184L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_51() {
		long[] data = {-3547085767303234256L, 69544110456831L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_52() {
		long[] data = {-3547085801662972624L, 34359738367999L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_53() {
		long[] data = {4597752725559295744L, 54077555145179136L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_54() {
		long[] data = {134217728L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_55() {
		long[] data = {-9223372034707292160L, 1023L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_56() {
		long[] data = {-422212465066126L, 288230376151711743L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_57() {
		long[] data = {335691008L, 274756272128L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_58() {
		long[] data = {-4611685736771322576L, 137438953471L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_59() {
		long[] data = {469908736L, 137317318656L, 0L, 0L};
		return data;
	}

	Qualident xy;

	private static final long[] mk_tokenSet_60() {
		long[] data = {274877906944L, 229376L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_61() {
		long[] data = {524544L, 17944132844519424L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_62() {
		long[] data = {-3548836189814653648L, 69544110456831L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_63() {
		long[] data = {-4125296976477815504L, 34359738367999L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_64() {
		long[] data = {1064670304188218624L, 35184250454016L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_65() {
		long[] data = {-142L, 288230376151711743L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_66() {
		long[] data = {137640952064L, 9147936743096320L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_67() {
		long[] data = {134742272L, 15762598695796736L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_68() {
		long[] data = {-4925812158496910L, 63119938893643775L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_69() {
		long[] data = {-13933011413237902L, 63119938893643775L, 0L, 0L};
		return data;
	}

	public ElijjahParser(ParserSharedInputState state) {
		super(state, 2);
		tokenNames = _tokenNames;
	}

	private static final long[] mk_tokenSet_70() {
		long[] data = {524544L, 15762598695796736L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_71() {
		long[] data = {137573696256L, 9007199254740992L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_72() {
		long[] data = {137573171200L, 0L, 0L};
		return data;
	}

	public ElijjahParser(TokenBuffer tokenBuf) {
		this(tokenBuf, 2);
	}

	protected ElijjahParser(TokenBuffer tokenBuf, int k) {
		super(tokenBuf, k);
		tokenNames = _tokenNames;
	}

	public ElijjahParser(TokenStream lexer) {
		this(lexer, 2);
	}

	protected ElijjahParser(TokenStream lexer, int k) {
		super(lexer, k);
		tokenNames = _tokenNames;
	}

	private static final long[] mk_tokenSet_2() {
		long[] data = {-13933011413237902L, 54253477127258111L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_3() {
		long[] data = {4597752588120341826L, 54077555145179136L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_4() {
		long[] data = {-422212465066126L, 270286346386407423L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_5() {
		long[] data = {4163586232301486402L, 54043195528445952L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_6() {
		long[] data = {72057723021525314L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_7() {
		long[] data = {-13933011413237902L, 54112739638902783L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_8() {
		long[] data = {-422212531126414L, 54253477127258111L, 0L, 0L};
		return data;
	}

	private static final long[] mk_tokenSet_9() {
		long[] data = {268435456L, 0L, 0L};
		return data;
	}

	public final AccessNotation accessNotation() throws RecognitionException, TokenStreamException {
		AccessNotation acs;

		Token        category   = null;
		Token        shorthand  = null;
		Token        category1  = null;
		Token        shorthand1 = null;
		TypeNameList tnl        = null;
		acs = new AccessNotationImpl();

		try {      // for error handling
			match(LITERAL_access);
			{
				if ((LA(1) == STRING_LITERAL) && (LA(2) == IDENT || LA(2) == LCURLY)) {
					category = LT(1);
					match(STRING_LITERAL);
					{
						switch (LA(1)) {
						case IDENT: {
							shorthand = LT(1);
							match(IDENT);
							match(EQUAL);
							break;
						}
						case LCURLY: {
							break;
						}
						default: {
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
					}
					match(LCURLY);
					tnl = typeNameList2();
					match(RCURLY);
					if (inputState.guessing == 0) {
						acs.setCategory(category);
						acs.setShortHand(shorthand);
						acs.setTypeNames(tnl);
					}
				} else if ((LA(1) == STRING_LITERAL) && (_tokenSet_23.member(LA(2)))) {
					category1 = LT(1);
					match(STRING_LITERAL);
					if (inputState.guessing == 0) {
						acs.setCategory(category1);
					}
				} else if ((LA(1) == IDENT || LA(1) == LCURLY)) {
					{
						switch (LA(1)) {
						case IDENT: {
							shorthand1 = LT(1);
							match(IDENT);
							match(EQUAL);
							break;
						}
						case LCURLY: {
							break;
						}
						default: {
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
					}
					match(LCURLY);
					tnl = typeNameList2();
					match(RCURLY);
					if (inputState.guessing == 0) {
						acs.setShortHand(shorthand1);
						acs.setTypeNames(tnl);
					}
				} else {
					throw new NoViableAltException(LT(1), getFilename());
				}

			}
			opt_semi();
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_23);
			} else {
				throw ex;
			}
		}
		return acs;
	}

	public final @Nullable IExpression additiveExpression() throws RecognitionException, TokenStreamException {
		IExpression ee;

		ee = null;
		ExpressionKind e2 = null;
		IExpression    e3 = null;

		try {      // for error handling
			ee = multiplicativeExpression();
			{
				_loop213:
				do {
					if ((LA(1) == PLUS || LA(1) == MINUS) && (_tokenSet_18.member(LA(2)))) {
						{
							switch (LA(1)) {
							case PLUS: {
								match(PLUS);
								if (inputState.guessing == 0) {
									e2 = ExpressionKind.ADDITION;
								}
								break;
							}
							case MINUS: {
								match(MINUS);
								if (inputState.guessing == 0) {
									e2 = ExpressionKind.SUBTRACTION;
								}
								break;
							}
							default: {
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
						}
						e3 = multiplicativeExpression();
						if (inputState.guessing == 0) {
							ee = ExpressionBuilder.build(ee, e2, e3);
						}
					} else {
						break _loop213;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final AliasStatement aliasStatement(
			OS_Element cont
											  ) throws RecognitionException, TokenStreamException {
		AliasStatement pc;

		IdentExpression i1 = null;
		pc = new AliasStatementImpl(cont);

		try {      // for error handling
			match(LITERAL_alias);
			i1 = ident();
			if (inputState.guessing == 0) {
				pc.setName(i1);
			}
			match(BECOMES);
			xy = qualident();
			if (inputState.guessing == 0) {
				pc.setExpression(xy);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return pc;
	}

	public final @Nullable IExpression andExpression() throws RecognitionException, TokenStreamException {
		IExpression ee;

		ee = null;
		IExpression e3 = null;

		try {      // for error handling
			ee = equalityExpression();
			{
				_loop196:
				do {
					if ((LA(1) == BAND) && (_tokenSet_18.member(LA(2)))) {
						match(BAND);
						e3 = equalityExpression();
						if (inputState.guessing == 0) {
							ee = ExpressionBuilder.build(ee, ExpressionKind.BAND, e3);
						}
					} else {
						break _loop196;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final AnnotationClause annotation_clause() throws RecognitionException, TokenStreamException {
		AnnotationClause a;

		Qualident      q  = null;
		ExpressionList el = null;
		a = new AnnotationClauseImpl();
		AnnotationPart ap = null;

		try {      // for error handling
			match(ANNOT);
			{
				int _cnt34 = 0;
				_loop34:
				do {
					if ((LA(1) == IDENT)) {
						if (inputState.guessing == 0) {
							ap = new AnnotationPartImpl();
						}
						q = qualident();
						if (inputState.guessing == 0) {
							ap.setClass(q);
						}
						{
							switch (LA(1)) {
							case LPAREN: {
								match(LPAREN);
								el = expressionList();
								match(RPAREN);
								if (inputState.guessing == 0) {
									ap.setExprs(el);
								}
								break;
							}
							case IDENT:
							case RBRACK: {
								break;
							}
							default: {
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
						}
						if (inputState.guessing == 0) {
							a.add(ap);
						}
					} else {
						if (_cnt34 >= 1) {
							break _loop34;
						} else {
							throw new NoViableAltException(LT(1), getFilename());
						}
					}

					_cnt34++;
				} while (true);
			}
			match(RBRACK);
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_22);
			} else {
				throw ex;
			}
		}
		return a;
	}

	public final @Nullable IExpression assignmentExpression() throws RecognitionException, TokenStreamException {
		IExpression ee;

		ee = null;
		IExpression    e  = null;
		IExpression    e2;
		ExpressionKind ek = null;

		try {      // for error handling
			ee = conditionalExpression();
			{
				if ((_tokenSet_55.member(LA(1))) && (_tokenSet_18.member(LA(2)))) {
					{
						switch (LA(1)) {
						case BECOMES: {
							match(BECOMES);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.ASSIGNMENT);
							}
							break;
						}
						case PLUS_ASSIGN: {
							match(PLUS_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_PLUS);
							}
							break;
						}
						case MINUS_ASSIGN: {
							match(MINUS_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_MINUS);
							}
							break;
						}
						case STAR_ASSIGN: {
							match(STAR_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_MULT);
							}
							break;
						}
						case DIV_ASSIGN: {
							match(DIV_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_DIV);
							}
							break;
						}
						case MOD_ASSIGN: {
							match(MOD_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_MOD);
							}
							break;
						}
						case SR_ASSIGN: {
							match(SR_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_SR);
							}
							break;
						}
						case BSR_ASSIGN: {
							match(BSR_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_BSR);
							}
							break;
						}
						case SL_ASSIGN: {
							match(SL_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_SL);
							}
							break;
						}
						case BAND_ASSIGN: {
							match(BAND_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_BAND);
							}
							break;
						}
						case BXOR_ASSIGN: {
							match(BXOR_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_BXOR);
							}
							break;
						}
						case BOR_ASSIGN: {
							match(BOR_ASSIGN);
							if (inputState.guessing == 0) {
								ek = (ExpressionKind.AUG_BOR);
							}
							break;
						}
						default: {
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
					}
					e2 = assignmentExpression();
					if (inputState.guessing == 0) {
						ee = ExpressionBuilder.build(ee, ek, e2);
					}
				} else if ((_tokenSet_7.member(LA(1))) && (_tokenSet_56.member(LA(2)))) {
				} else {
					throw new NoViableAltException(LT(1), getFilename());
				}

			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final void caseConditional(
			CaseConditional mc
									 ) throws RecognitionException, TokenStreamException {

		CaseContext ctx   = null;
		IExpression expr1 = null;

		try {      // for error handling
			match(LITERAL_case);
			expr = expression();
			if (inputState.guessing == 0) {
				mc.expr(expr);
			}
			match(LCURLY);
			{
				_loop255:
				do {
					if ((_tokenSet_18.member(LA(1)))) {
						expr1 = expression();
						sco   = scope3(mc);
						if (inputState.guessing == 0) {
							mc.scope(sco, expr1);
						}
					} else {
						break _loop255;
					}

				} while (true);
			}
			match(RCURLY);
			if (inputState.guessing == 0) {
				mc.postConstruct();
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_39);
			} else {
				throw ex;
			}
		}
	}

	public final @Nullable ClassHeader class_header(
			List<AnnotationClause> as
												   ) throws RecognitionException, TokenStreamException {
		ClassHeader ch;

		ch = null;
		boolean         extends_   = false;
		IdentExpression class_name = null;
		TypeNameList    tnl        = null;

		try {      // for error handling
			{
				switch (LA(1)) {
				case LITERAL_extend: {
					match(LITERAL_extend);
					if (inputState.guessing == 0) {
						extends_ = true;
					}
					break;
				}
				case LITERAL_class: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			match(LITERAL_class);
			if (inputState.guessing == 0) {
				ch = new ClassHeaderImpl(extends_, as);
			}
			class_modifier(ch);
			class_name = ident();
			if (inputState.guessing == 0) {
				ch.setName(class_name);
			}
			{
				switch (LA(1)) {
				case LBRACK: {
					match(LBRACK);
					tnl = typeNameList2();
					match(RBRACK);
					if (inputState.guessing == 0) {
						ch.setGenericPart(tnl);
					}
					break;
				}
				case LITERAL_const:
				case LPAREN:
				case LCURLY:
				case LT_: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			{
				switch (LA(1)) {
				case LPAREN:
				case LT_: {
					class_inheritance(ch);
					break;
				}
				case LITERAL_const:
				case LCURLY: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			{
				switch (LA(1)) {
				case LITERAL_const: {
					match(LITERAL_const);
					if (inputState.guessing == 0) {
						ch.setConst(true);
					}
					break;
				}
				case LCURLY: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_9);
			} else {
				throw ex;
			}
		}
		return ch;
	}

	public final void class_inheritance(
			ClassHeader ch
									   ) throws RecognitionException, TokenStreamException {


		try {      // for error handling
			switch (LA(1)) {
			case LPAREN: {
				match(LPAREN);
				classInheritance_(ch.inheritancePart());
				match(RPAREN);
				break;
			}
			case LT_: {
				classInheritanceRuby(ch.inheritancePart());
				break;
			}
			default: {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_12);
			} else {
				throw ex;
			}
		}
	}

	public final void classInheritance_(
			ClassInheritance ci
									   ) throws RecognitionException, TokenStreamException {

		TypeName tn = null;

		try {      // for error handling
			tn = inhTypeName();
			if (inputState.guessing == 0) {
				ci.add(tn);
			}
			{
				_loop54:
				do {
					if ((LA(1) == COMMA)) {
						match(COMMA);
						tn = inhTypeName();
						if (inputState.guessing == 0) {
							ci.add(tn);
						}
					} else {
						break _loop54;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_13);
			} else {
				throw ex;
			}
		}
	}

	public final void classInheritanceRuby(
			ClassInheritance ci
										  ) throws RecognitionException, TokenStreamException {


		try {      // for error handling
			match(LT_);
			classInheritance_(ci);
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_12);
			} else {
				throw ex;
			}
		}
	}

	public final void class_modifier(
			ClassHeader ch
									) throws RecognitionException, TokenStreamException {


		try {      // for error handling
			{
				switch (LA(1)) {
				case LITERAL_struct: {
					match(LITERAL_struct);
					if (inputState.guessing == 0) {
						ch.setType(ClassTypes.STRUCTURE);
					}
					break;
				}
				case LITERAL_signature: {
					match(LITERAL_signature);
					if (inputState.guessing == 0) {
						ch.setType(ClassTypes.SIGNATURE);
					}
					break;
				}
				case LITERAL_interface: {
					match(LITERAL_interface);
					if (inputState.guessing == 0) {
						ch.setType(ClassTypes.INTERFACE);
					}
					break;
				}
				case LITERAL_abstract: {
					match(LITERAL_abstract);
					if (inputState.guessing == 0) {
						ch.setType(ClassTypes.ABSTRACT);
					}
					break;
				}
				case LITERAL_annotation: {
					match(LITERAL_annotation);
					if (inputState.guessing == 0) {
						ch.setType(ClassTypes.ANNOTATION);
					}
					break;
				}
				case LITERAL_exception: {
					match(LITERAL_exception);
					if (inputState.guessing == 0) {
						ch.setType(ClassTypes.EXCEPTION);
					}
					break;
				}
				case IDENT: {
					if (inputState.guessing == 0) {
						ch.setType(ClassTypes.NORMAL);
					}
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_10);
			} else {
				throw ex;
			}
		}
	}

	public final void classScope(
			ClassStatement cr
								) throws RecognitionException, TokenStreamException {

		AccessNotation         acs = null;
		TypeAliasStatement     tal = null;
		BaseFunctionDef        fd  = null;
		List<AnnotationClause> as  = new ArrayList<AnnotationClause>();
		AnnotationClause       a   = null;

		try {      // for error handling
			docstrings(cr);
			{
				_loop29:
				do {
					switch (LA(1)) {
					case LITERAL_constructor:
					case LITERAL_ctor: {
						constructorDef(cr);
						break;
					}
					case LITERAL_destructor:
					case LITERAL_dtor: {
						destructorDef(cr);
						break;
					}
					case LITERAL_const:
					case LITERAL_var:
					case LITERAL_val: {
						varStmt(cr.statementClosure(), cr);
						break;
					}
					case LITERAL_prop:
					case LITERAL_property: {
						propertyStatement(cr.prop());
						break;
					}
					case LITERAL_access: {
						acs = accessNotation();
						if (inputState.guessing == 0) {
							cr.addAccess(acs);
						}
						break;
					}
					default:
						if ((LA(1) == IDENT || LA(1) == ANNOT || LA(1) == LITERAL_def) && (_tokenSet_14.member(LA(2)))) {
							{
								_loop26:
								do {
									if ((LA(1) == ANNOT)) {
										a = annotation_clause();
										if (inputState.guessing == 0) {
											as.add(a);
										}
									} else {
										break _loop26;
									}

								} while (true);
							}
							fd = function_definition(cr, cr.getContext(), as);
						} else if ((LA(1) == LITERAL_def) && (LA(2) == IDENT)) {
							defFunctionDef(cr.defFuncDef());
						} else if ((LA(1) == LITERAL_type) && (LA(2) == IDENT)) {
							match(LITERAL_type);
							match(IDENT);
							match(BECOMES);
							match(IDENT);
							{
								_loop28:
								do {
									if ((LA(1) == BOR)) {
										match(BOR);
										match(IDENT);
									} else {
										break _loop28;
									}

								} while (true);
							}
						} else if ((LA(1) == LITERAL_type) && (LA(2) == LITERAL_alias)) {
							tal = typeAlias(cr);
							if (inputState.guessing == 0) {
								cr.addToContainer(tal);
							}
						} else if ((_tokenSet_15.member(LA(1))) && (_tokenSet_16.member(LA(2)))) {
							programStatement(/*cr.XXX(),*/ cr);
						} else {
							break _loop29;
						}
					}
				} while (true);
			}
			{
				switch (LA(1)) {
				case LITERAL_invariant: {
					invariantStatement(cr.invariantStatement());
					break;
				}
				case RCURLY: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_17);
			} else {
				throw ex;
			}
		}
	}

	public final @Nullable ClassStatement classStatement(
			OS_Element parent, Context cctx, List<AnnotationClause> as
														) throws RecognitionException, TokenStreamException {
		ClassStatement cls;

		cls = null;
		ClassContext    ctx = null;
		IdentExpression i1  = null;
		TypeNameList    tnl = null;
		ClassHeader     ch  = null;

		try {      // for error handling
			ch = class_header(as);
			if (inputState.guessing == 0) {
				cls = new ClassStatementImpl(parent, cctx);
				cls.setHeader(ch);
			}
			match(LCURLY);
			if (inputState.guessing == 0) {
				ctx = cls.getContext();
				cur = ctx;
			}
			{
				switch (LA(1)) {
				case IDENT:
				case STRING_LITERAL:
				case LITERAL_extend:
				case LITERAL_class:
				case LITERAL_const:
				case RCURLY:
				case LITERAL_type:
				case ANNOT:
				case LITERAL_namespace:
				case LITERAL_from:
				case LITERAL_import:
				case LITERAL_constructor:
				case LITERAL_ctor:
				case LITERAL_destructor:
				case LITERAL_dtor:
				case LITERAL_def:
				case LITERAL_var:
				case LITERAL_val:
				case LITERAL_alias:
				case LITERAL_invariant:
				case LITERAL_access:
				case LITERAL_prop:
				case LITERAL_property: {
					classScope(cls);
					break;
				}
				case LITERAL_abstract: {
					match(LITERAL_abstract);
					if (inputState.guessing == 0) {
						cls.setType(ClassTypes.ABSTRACT);
					}
					{
						switch (LA(1)) {
						case LITERAL_invariant: {
							invariantStatement(cls.invariantStatement());
							break;
						}
						case RCURLY: {
							break;
						}
						default: {
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
					}
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			match(RCURLY);
			if (inputState.guessing == 0) {
				cls.postConstruct();
				cur = ctx.getParent();
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_3);
			} else {
				throw ex;
			}
		}
		return cls;
	}

	public final @Nullable IExpression conditionalExpression() throws RecognitionException, TokenStreamException {
		IExpression ee;

		ee = null;

		try {      // for error handling
			ee = logicalOrExpression();
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable IExpression constantValue() throws RecognitionException, TokenStreamException {
		IExpression e;

		Token s = null;
		Token c = null;
		Token n = null;
		Token f = null;
		e = null;

		try {      // for error handling
			switch (LA(1)) {
			case STRING_LITERAL: {
				s = LT(1);
				match(STRING_LITERAL);
				if (inputState.guessing == 0) {
					e = new StringExpressionImpl(s);
				}
				break;
			}
			case CHAR_LITERAL: {
				c = LT(1);
				match(CHAR_LITERAL);
				if (inputState.guessing == 0) {
					e = new CharLitExpressionImpl(c);
				}
				break;
			}
			case NUM_INT: {
				n = LT(1);
				match(NUM_INT);
				if (inputState.guessing == 0) {
					e = new NumericExpressionImpl(n);
				}
				break;
			}
			case NUM_FLOAT: {
				f = LT(1);
				match(NUM_FLOAT);
				if (inputState.guessing == 0) {
					e = new FloatExpressionImpl(f);
				}
				break;
			}
			default: {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return e;
	}

	public final void constructExpression(
			StatementClosure cr
										 ) throws RecognitionException, TokenStreamException {

		Qualident      q = null;
		ExpressionList o = null;

		try {      // for error handling
			match(LITERAL_construct);
			q = qualident();
			{
				if ((LA(1) == LPAREN) && (_tokenSet_59.member(LA(2)))) {
					match(LPAREN);
					{
						switch (LA(1)) {
						case IDENT:
						case STRING_LITERAL:
						case CHAR_LITERAL:
						case NUM_INT:
						case NUM_FLOAT:
						case LBRACK:
						case LPAREN:
						case LCURLY:
						case PLUS:
						case MINUS:
						case INC:
						case DEC:
						case BNOT:
						case LNOT:
						case LITERAL_true:
						case LITERAL_false:
						case LITERAL_this:
						case LITERAL_null:
						case LITERAL_function:
						case LITERAL_procedure: {
							o = expressionList();
							break;
						}
						case RPAREN: {
							break;
						}
						default: {
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
					}
					match(RPAREN);
				} else if ((_tokenSet_39.member(LA(1))) && (_tokenSet_34.member(LA(2)))) {
				} else {
					throw new NoViableAltException(LT(1), getFilename());
				}

			}
			if (inputState.guessing == 0) {
				cr.constructExpression(q, o);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_39);
			} else {
				throw ex;
			}
		}
	}

	public final void constructorDef(
			ClassStatement cr
									) throws RecognitionException, TokenStreamException {

		ConstructorDef  cd  = null;
		IdentExpression x1  = null;
		FormalArgList   fal = null;

		try {      // for error handling
			{
				switch (LA(1)) {
				case LITERAL_constructor: {
					match(LITERAL_constructor);
					break;
				}
				case LITERAL_ctor: {
					match(LITERAL_ctor);
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			{
				switch (LA(1)) {
				case IDENT: {
					x1 = ident();
					if (inputState.guessing == 0) {
						cd = cr.addCtor(x1);
					}
					break;
				}
				case LPAREN: {
					if (inputState.guessing == 0) {
						cd = cr.addCtor(null);
					}
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			fal = opfal();
			if (inputState.guessing == 0) {
				cd.setFal(fal);
			}
			sco = scope3(cd);
			if (inputState.guessing == 0) {
				cd.scope(sco);
			}
			if (inputState.guessing == 0) {
				cd.postConstruct();
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_21);
			} else {
				throw ex;
			}
		}
	}

	public final @Nullable DefFunctionDef def_function_definition(
			OS_Element parent, Context ctx, List<AnnotationClause> as
																 ) throws RecognitionException, TokenStreamException {
		DefFunctionDef fd;

		fd = null;
		FunctionHeader fh = null;
		IExpression    fb = null;

		try {      // for error handling
			match(LITERAL_def);
			fh = function_header();
			if (inputState.guessing == 0) {
				fd  = new DefFunctionDefImpl(parent, ctx);
				cur = fd.getContext();
			}
			fb = expression();
			{
				if ((_tokenSet_23.member(LA(1))) && (_tokenSet_48.member(LA(2)))) {
					opt_semi();
				} else if ((_tokenSet_23.member(LA(1))) && (_tokenSet_48.member(LA(2)))) {
				} else {
					throw new NoViableAltException(LT(1), getFilename());
				}

			}
			if (inputState.guessing == 0) {
				fd.setAnnotations(as);
				fd.setHeader(fh);
				fd.setBody(fb);
				cur = ctx.getParent();
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_23);
			} else {
				throw ex;
			}
		}
		return fd;
	}

	public final void defFunctionDef(
			DefFunctionDef fd
									) throws RecognitionException, TokenStreamException {

		FormalArgList   op = null;
		TypeName        tn = null;
		IdentExpression i1 = null;

		try {      // for error handling
			match(LITERAL_def);
			i1 = ident();
			op = opfal();
			{
				switch (LA(1)) {
				case TOK_COLON:
				case TOK_ARROW: {
					{
						switch (LA(1)) {
						case TOK_COLON: {
							match(TOK_COLON);
							break;
						}
						case TOK_ARROW: {
							match(TOK_ARROW);
							break;
						}
						default: {
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
					}
					tn = typeName2();
					if (inputState.guessing == 0) {
						fd.setReturnType(tn);
					}
					break;
				}
				case BECOMES: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			match(BECOMES);
			expr = expression();
			if (inputState.guessing == 0) {
				fd.setSpecies(FunctionDef.Species.DEF_FUN);
				fd.setName(i1);
				fd.setFal(op);
				fd.setExpr(expr);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_21);
			} else {
				throw ex;
			}
		}
	}

	public final void destructorDef(
			ClassStatement cr
								   ) throws RecognitionException, TokenStreamException {

		DestructorDef dd  = null;
		FormalArgList fal = null;

		try {      // for error handling
			{
				switch (LA(1)) {
				case LITERAL_destructor: {
					match(LITERAL_destructor);
					break;
				}
				case LITERAL_dtor: {
					match(LITERAL_dtor);
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			if (inputState.guessing == 0) {
				dd = cr.addDtor();
			}
			fal = opfal();
			if (inputState.guessing == 0) {
				dd.setFal(fal);
			}
			sco = scope3(dd);
			if (inputState.guessing == 0) {
				dd.scope(sco);
			}
			if (inputState.guessing == 0) {
				dd.postConstruct();
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_21);
			} else {
				throw ex;
			}
		}
	}

	public final void docstrings(
			@Nullable Documentable sc
								) throws RecognitionException, TokenStreamException {

		Token s1 = null;

		try {      // for error handling
			{
				boolean synPredMatched59 = false;
				if (((LA(1) == STRING_LITERAL) && (_tokenSet_20.member(LA(2))))) {
					int _m59 = mark();
					synPredMatched59 = true;
					inputState.guessing++;
					try {
						{
							match(STRING_LITERAL);
						}
					} catch (RecognitionException pe) {
						synPredMatched59 = false;
					}
					rewind(_m59);
					inputState.guessing--;
				}
				if (synPredMatched59) {
					{
						int _cnt61 = 0;
						_loop61:
						do {
							if ((LA(1) == STRING_LITERAL) && (_tokenSet_20.member(LA(2)))) {
								s1 = LT(1);
								match(STRING_LITERAL);
								if (inputState.guessing == 0) {
									if (sc != null) sc.addDocString(s1);
								}
							} else {
								if (_cnt61 >= 1) {
									break _loop61;
								} else {
									throw new NoViableAltException(LT(1), getFilename());
								}
							}

							_cnt61++;
						} while (true);
					}
				} else if ((_tokenSet_20.member(LA(1))) && (_tokenSet_4.member(LA(2)))) {
				} else {
					throw new NoViableAltException(LT(1), getFilename());
				}

			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_20);
			} else {
				throw ex;
			}
		}
	}

	public final @Nullable IExpression dot_expression_or_procedure_call(
			IExpression e1
																	   ) throws RecognitionException, TokenStreamException {
		IExpression ee;

		Token lp2 = null;
		ee = null;
		ExpressionList  el = null;
		IdentExpression e  = null;

		try {      // for error handling
			e = ident();
			if (inputState.guessing == 0) {
				ee = new DotExpressionImpl(e1, e);
			}
			{
				if ((LA(1) == LPAREN) && (_tokenSet_59.member(LA(2)))) {
					lp2 = LT(1);
					match(LPAREN);
					{
						switch (LA(1)) {
						case IDENT:
						case STRING_LITERAL:
						case CHAR_LITERAL:
						case NUM_INT:
						case NUM_FLOAT:
						case LBRACK:
						case LPAREN:
						case LCURLY:
						case PLUS:
						case MINUS:
						case INC:
						case DEC:
						case BNOT:
						case LNOT:
						case LITERAL_true:
						case LITERAL_false:
						case LITERAL_this:
						case LITERAL_null:
						case LITERAL_function:
						case LITERAL_procedure: {
							el = expressionList();
							break;
						}
						case RPAREN: {
							break;
						}
						default: {
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
					}
					if (inputState.guessing == 0) {
						ProcedureCallExpression pce = new ProcedureCallExpressionImpl();
						pce.identifier(ee);
						pce.setArgs(el);
						ee = pce;
					}
					match(RPAREN);
				} else if ((_tokenSet_7.member(LA(1))) && (_tokenSet_56.member(LA(2)))) {
				} else {
					throw new NoViableAltException(LT(1), getFilename());
				}

			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final void elseif_part(
			IfConditional ifex
								 ) throws RecognitionException, TokenStreamException {


		try {      // for error handling
			{
				switch (LA(1)) {
				case LITERAL_elseif: {
					match(LITERAL_elseif);
					break;
				}
				case LITERAL_else: {
					match(LITERAL_else);
					match(LITERAL_if);
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			expr = expression();
			if (inputState.guessing == 0) {
				ifex.expr(expr);
				cur = ifex.getContext();
			}
			sco = scope3(ifex);
			if (inputState.guessing == 0) {
				ifex.scope(sco);
			}
			if (inputState.guessing == 0) {
				cur = cur.getParent();
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_64);
			} else {
				throw ex;
			}
		}
	}

	public final @Nullable IExpression equalityExpression() throws RecognitionException, TokenStreamException {
		IExpression ee;

		ee = null;
		ExpressionKind e2 = null;
		IExpression    e3 = null;

		try {      // for error handling
			ee = relationalExpression();
			{
				_loop200:
				do {
					if ((LA(1) == EQUAL || LA(1) == NOT_EQUAL) && (_tokenSet_18.member(LA(2)))) {
						{
							switch (LA(1)) {
							case NOT_EQUAL: {
								match(NOT_EQUAL);
								if (inputState.guessing == 0) {
									e2 = ExpressionKind.NOT_EQUAL;
								}
								break;
							}
							case EQUAL: {
								match(EQUAL);
								if (inputState.guessing == 0) {
									e2 = ExpressionKind.EQUAL;
								}
								break;
							}
							default: {
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
						}
						e3 = relationalExpression();
						if (inputState.guessing == 0) {
							ee = ExpressionBuilder.build(ee, e2, e3);
						}
					} else {
						break _loop200;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable IExpression exclusiveOrExpression() throws RecognitionException, TokenStreamException {
		IExpression ee;

		ee = null;
		IExpression e3 = null;

		try {      // for error handling
			ee = andExpression();
			{
				_loop193:
				do {
					if ((LA(1) == BXOR) && (_tokenSet_18.member(LA(2)))) {
						match(BXOR);
						e3 = andExpression();
						if (inputState.guessing == 0) {
							ee = ExpressionBuilder.build(ee, ExpressionKind.BXOR, e3);
						}
					} else {
						break _loop193;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable IExpression expression() throws RecognitionException, TokenStreamException {
		IExpression ee;

		ee = null;

		try {      // for error handling
			ee = assignmentExpression();
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final ExpressionList expressionList() throws RecognitionException, TokenStreamException {
		ExpressionList el;

		el = new ExpressionListImpl();

		try {      // for error handling
			expr = expression();
			if (inputState.guessing == 0) {
				el.next(expr);
			}
			{
				_loop169:
				do {
					if ((LA(1) == COMMA)) {
						match(COMMA);
						expr = expression();
						if (inputState.guessing == 0) {
							el.next(expr);
						}
					} else {
						break _loop169;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_6);
			} else {
				throw ex;
			}
		}
		return el;
	}

	public final void frobeIteration(
			StatementClosure cr
									) throws RecognitionException, TokenStreamException {

		Loop            loop = cr.loop();
		LoopContext     ctx  = null;
		IdentExpression i1   = null, i2 = null, i3 = null;

		try {      // for error handling
			match(LITERAL_iterate);
			if (inputState.guessing == 0) {
				ctx = new LoopContext(cur, loop);
				loop.setContext(ctx);
				cur = ctx;
			}
			{
				switch (LA(1)) {
				case LITERAL_from: {
					match(LITERAL_from);
					if (inputState.guessing == 0) {
						loop.type(LoopTypes.FROM_TO_TYPE);
					}
					expr = expression();
					if (inputState.guessing == 0) {
						loop.frompart(expr);
					}
					match(LITERAL_to);
					expr = expression();
					if (inputState.guessing == 0) {
						loop.topart(expr);
					}
					{
						switch (LA(1)) {
						case LITERAL_with: {
							match(LITERAL_with);
							i1 = ident();
							if (inputState.guessing == 0) {
								loop.iterName(i1);
							}
							break;
						}
						case LCURLY: {
							break;
						}
						default: {
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
					}
					break;
				}
				case LITERAL_to: {
					match(LITERAL_to);
					if (inputState.guessing == 0) {
						loop.type(LoopTypes.TO_TYPE);
					}
					expr = expression();
					if (inputState.guessing == 0) {
						loop.topart(expr);
					}
					{
						switch (LA(1)) {
						case LITERAL_with: {
							match(LITERAL_with);
							i2 = ident();
							if (inputState.guessing == 0) {
								loop.iterName(i2);
							}
							break;
						}
						case LCURLY: {
							break;
						}
						default: {
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
					}
					break;
				}
				case IDENT:
				case STRING_LITERAL:
				case CHAR_LITERAL:
				case NUM_INT:
				case NUM_FLOAT:
				case LBRACK:
				case LPAREN:
				case LCURLY:
				case PLUS:
				case MINUS:
				case INC:
				case DEC:
				case BNOT:
				case LNOT:
				case LITERAL_true:
				case LITERAL_false:
				case LITERAL_this:
				case LITERAL_null:
				case LITERAL_function:
				case LITERAL_procedure: {
					if (inputState.guessing == 0) {
						loop.type(LoopTypes.EXPR_TYPE);
					}
					expr = expression();
					if (inputState.guessing == 0) {
						loop.topart(expr);
					}
					{
						switch (LA(1)) {
						case LITERAL_with: {
							match(LITERAL_with);
							i3 = ident();
							if (inputState.guessing == 0) {
								loop.iterName(i3);
							}
							break;
						}
						case LCURLY: {
							break;
						}
						default: {
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
					}
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			sco = scope3(loop);
			if (inputState.guessing == 0) {
				loop.scope(sco);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_39);
			} else {
				throw ex;
			}
		}
	}

	public final void funcExpr(
			FuncExpr pc
							  ) throws RecognitionException, TokenStreamException {

		Scope3          sc  = null;
		TypeName        tn  = null;
		FuncExprContext ctx = null;
		FormalArgList   fal = null;

		try {      // for error handling
			{
				switch (LA(1)) {
				case LITERAL_function: {
					match(LITERAL_function);
					if (inputState.guessing == 0) {
						pc.type(TypeModifiers.FUNCTION);
					}
					{
						fal = opfal();
						if (inputState.guessing == 0) {
							pc.setArgList(fal);
						}
					}
					if (inputState.guessing == 0) {
						ctx = new FuncExprContext(cur, pc);
						pc.setContext(ctx);
						cur = ctx;
					}
					sco = scope3(pc);
					if (inputState.guessing == 0) {
						pc.scope(sco);
					}
					{
						if ((LA(1) == TOK_COLON || LA(1) == TOK_ARROW) && (_tokenSet_61.member(LA(2)))) {
							{
								switch (LA(1)) {
								case TOK_ARROW: {
									match(TOK_ARROW);
									break;
								}
								case TOK_COLON: {
									match(TOK_COLON);
									break;
								}
								default: {
									throw new NoViableAltException(LT(1), getFilename());
								}
								}
							}
							tn = typeName2();
							if (inputState.guessing == 0) {
								pc.setReturnType(tn);
							}
						} else if ((_tokenSet_7.member(LA(1))) && (_tokenSet_56.member(LA(2)))) {
						} else {
							throw new NoViableAltException(LT(1), getFilename());
						}

					}
					break;
				}
				case LITERAL_procedure: {
					match(LITERAL_procedure);
					if (inputState.guessing == 0) {
						pc.type(TypeModifiers.PROCEDURE);
					}
					{
						fal = opfal();
						if (inputState.guessing == 0) {
							pc.setArgList(fal);
						}
					}
					if (inputState.guessing == 0) {
						ctx = new FuncExprContext(cur, pc);
						pc.setContext(ctx);
						cur = ctx;
					}
					sco = scope3(pc);
					if (inputState.guessing == 0) {
						pc.scope(sco);
					}
					break;
				}
				case LCURLY: {
					if (inputState.guessing == 0) {
						sc = new Scope3Impl(pc);
					}
					match(LCURLY);
					if (inputState.guessing == 0) {
						ctx = new FuncExprContext(cur, pc);
						pc.setContext(ctx);
						cur = ctx;
					}
					match(BOR);
					{
						switch (LA(1)) {
						case LPAREN: {
							fal = opfal();
							if (inputState.guessing == 0) {
								pc.setArgList(fal);
							}
							break;
						}
						case BOR: {
							break;
						}
						default: {
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
					}
					match(BOR);
					{
						_loop241:
						do {
							if ((_tokenSet_35.member(LA(1))) && (_tokenSet_62.member(LA(2)))) {
								statement(sc.statementClosure(), sc.getParent());
							} else if ((_tokenSet_18.member(LA(1))) && (_tokenSet_63.member(LA(2)))) {
								expr = expression();
								if (inputState.guessing == 0) {
									sc.statementWrapper(expr);
								}
							} else if ((LA(1) == LITERAL_extend || LA(1) == LITERAL_class)) {
								classStatement(sc.getParent(), cur, null/*annotations*/);
							} else {
								break _loop241;
							}

						} while (true);
					}
					match(RCURLY);
					if (inputState.guessing == 0) {
						pc.scope(sc);
					}
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			if (inputState.guessing == 0) {
				pc.postConstruct();
				cur = cur.getParent();
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
	}

	public final FunctionBody function_body_mandatory(
			OS_Element parent
													 ) throws RecognitionException, TokenStreamException {
		FunctionBody fb;

		fb = new FunctionBodyImpl();
		Scope3         sc  = new Scope3Impl(parent);
		ClassStatement cls = null;
		fb.setScope3(sc);

		try {      // for error handling
			match(LCURLY);
			docstrings(sc);
			{
				switch (LA(1)) {
				case LITERAL_pre:
				case LITERAL_requires: {
					preConditionSegment(fb);
					break;
				}
				case IDENT:
				case STRING_LITERAL:
				case CHAR_LITERAL:
				case NUM_INT:
				case NUM_FLOAT:
				case LITERAL_extend:
				case LITERAL_class:
				case LBRACK:
				case LITERAL_const:
				case LITERAL_abstract:
				case LPAREN:
				case LCURLY:
				case RCURLY:
				case LITERAL_continue:
				case LITERAL_break:
				case LITERAL_return:
				case LITERAL_post:
				case LITERAL_ensures:
				case LITERAL_var:
				case LITERAL_val:
				case LITERAL_yield:
				case LITERAL_construct:
				case PLUS:
				case MINUS:
				case INC:
				case DEC:
				case BNOT:
				case LNOT:
				case LITERAL_true:
				case LITERAL_false:
				case LITERAL_this:
				case LITERAL_null:
				case LITERAL_function:
				case LITERAL_procedure:
				case LITERAL_if:
				case LITERAL_match:
				case LITERAL_case:
				case LITERAL_while:
				case LITERAL_do:
				case LITERAL_iterate: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			{
				switch (LA(1)) {
				case IDENT:
				case STRING_LITERAL:
				case CHAR_LITERAL:
				case NUM_INT:
				case NUM_FLOAT:
				case LITERAL_extend:
				case LITERAL_class:
				case LBRACK:
				case LITERAL_const:
				case LPAREN:
				case LCURLY:
				case RCURLY:
				case LITERAL_continue:
				case LITERAL_break:
				case LITERAL_return:
				case LITERAL_post:
				case LITERAL_ensures:
				case LITERAL_var:
				case LITERAL_val:
				case LITERAL_yield:
				case LITERAL_construct:
				case PLUS:
				case MINUS:
				case INC:
				case DEC:
				case BNOT:
				case LNOT:
				case LITERAL_true:
				case LITERAL_false:
				case LITERAL_this:
				case LITERAL_null:
				case LITERAL_function:
				case LITERAL_procedure:
				case LITERAL_if:
				case LITERAL_match:
				case LITERAL_case:
				case LITERAL_while:
				case LITERAL_do:
				case LITERAL_iterate: {
					{
						_loop122:
						do {
							if ((_tokenSet_50.member(LA(1)))) {
								{
									switch (LA(1)) {
									case LITERAL_extend:
									case LITERAL_class: {
										cls = classStatement(sc.getParent(), cur, null/*annotations*/);
										if (inputState.guessing == 0) {
											sc.add(cls);
										}
										break;
									}
									case LITERAL_continue: {
										match(LITERAL_continue);
										break;
									}
									case LITERAL_break: {
										match(LITERAL_break);
										break;
									}
									default:
										if ((_tokenSet_35.member(LA(1))) && (_tokenSet_51.member(LA(2)))) {
											statement(sc.statementClosure(), sc.getParent());
										} else if ((_tokenSet_18.member(LA(1))) && (_tokenSet_52.member(LA(2)))) {
											expr = expression();
											if (inputState.guessing == 0) {
												sc.statementWrapper(expr);
											}
										} else {
											throw new NoViableAltException(LT(1), getFilename());
										}
									}
								}
								opt_semi();
							} else {
								break _loop122;
							}

						} while (true);
					}
					{
						switch (LA(1)) {
						case LITERAL_return: {
							match(LITERAL_return);
							{
								switch (LA(1)) {
								case IDENT:
								case STRING_LITERAL:
								case CHAR_LITERAL:
								case NUM_INT:
								case NUM_FLOAT:
								case LBRACK:
								case LPAREN:
								case LCURLY:
								case PLUS:
								case MINUS:
								case INC:
								case DEC:
								case BNOT:
								case LNOT:
								case LITERAL_true:
								case LITERAL_false:
								case LITERAL_this:
								case LITERAL_null:
								case LITERAL_function:
								case LITERAL_procedure: {
									{
										expr = expression();
									}
									break;
								}
								case RCURLY:
								case LITERAL_post:
								case LITERAL_ensures: {
									break;
								}
								default: {
									throw new NoViableAltException(LT(1), getFilename());
								}
								}
							}
							break;
						}
						case RCURLY:
						case LITERAL_post:
						case LITERAL_ensures: {
							break;
						}
						default: {
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
					}
					break;
				}
				case LITERAL_abstract: {
					match(LITERAL_abstract);
					opt_semi();
					if (inputState.guessing == 0) {
						fb.setAbstract(true);
					}
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			{
				switch (LA(1)) {
				case LITERAL_post:
				case LITERAL_ensures: {
					postConditionSegment(fb);
					break;
				}
				case RCURLY: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			match(RCURLY);
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_23);
			} else {
				throw ex;
			}
		}
		return fb;
	}

	public final @Nullable BaseFunctionDef function_definition(
			OS_Element parent, Context ctx, List<AnnotationClause> as
															  ) throws RecognitionException, TokenStreamException {
		BaseFunctionDef fd;

		fd = null;

		try {      // for error handling
			switch (LA(1)) {
			case LITERAL_def: {
				fd = (BaseFunctionDef) def_function_definition(parent, ctx, as);
				if (inputState.guessing == 0) {
					fd.setSpecies(BaseFunctionDef.Species.DEF_FUN);
				}
				break;
			}
			case IDENT: {
				fd = (BaseFunctionDef) normal_function_definition(parent, ctx, as);
				if (inputState.guessing == 0) {
					fd.setSpecies(BaseFunctionDef.Species.REG_FUN);
				}
				break;
			}
			default: {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_23);
			} else {
				throw ex;
			}
		}
		return fd;
	}

	public final FunctionHeader function_header() throws RecognitionException, TokenStreamException {
		FunctionHeader fh;

		fh = new FunctionHeaderImpl();
		IdentExpression i1  = null;
		FormalArgList   fal = null;
		TypeName        tn  = null;

		try {      // for error handling
			i1 = ident();
			if (inputState.guessing == 0) {
				fh.setName(i1);
			}
			{
				switch (LA(1)) {
				case LITERAL_const: {
					match(LITERAL_const);
					if (inputState.guessing == 0) {
						fh.setModifier(FunctionModifiers.CONST);
					}
					break;
				}
				case LITERAL_immutable: {
					match(LITERAL_immutable);
					if (inputState.guessing == 0) {
						fh.setModifier(FunctionModifiers.IMMUTABLE);
					}
					break;
				}
				case LPAREN: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			fal = opfal();
			if (inputState.guessing == 0) {
				fh.setFal(fal);
			}
			{
				switch (LA(1)) {
				case TOK_ARROW: {
					match(TOK_ARROW);
					tn = typeName2();
					if (inputState.guessing == 0) {
						fh.setReturnType(tn);
					}
					break;
				}
				case IDENT:
				case STRING_LITERAL:
				case CHAR_LITERAL:
				case NUM_INT:
				case NUM_FLOAT:
				case LITERAL_extend:
				case LITERAL_class:
				case LBRACK:
				case LITERAL_const:
				case LPAREN:
				case LCURLY:
				case RCURLY:
				case LITERAL_type:
				case ANNOT:
				case LITERAL_namespace:
				case LITERAL_from:
				case LITERAL_import:
				case LITERAL_constructor:
				case LITERAL_ctor:
				case LITERAL_destructor:
				case LITERAL_dtor:
				case LITERAL_def:
				case LITERAL_var:
				case LITERAL_val:
				case LITERAL_alias:
				case SEMI:
				case LITERAL_invariant:
				case LITERAL_access:
				case PLUS:
				case MINUS:
				case INC:
				case DEC:
				case BNOT:
				case LNOT:
				case LITERAL_true:
				case LITERAL_false:
				case LITERAL_this:
				case LITERAL_null:
				case LITERAL_function:
				case LITERAL_procedure:
				case LITERAL_prop:
				case LITERAL_property: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_49);
			} else {
				throw ex;
			}
		}
		return fh;
	}

	public final @Nullable FunctionBody function_body(
			OS_Element parent
													 ) throws RecognitionException, TokenStreamException {
		FunctionBody fb;

		fb = null;

		try {      // for error handling
			switch (LA(1)) {
			case LCURLY: {
				fb = function_body_mandatory(parent);
				break;
			}
			case IDENT:
			case LITERAL_extend:
			case LITERAL_class:
			case LITERAL_const:
			case RCURLY:
			case LITERAL_type:
			case ANNOT:
			case LITERAL_namespace:
			case LITERAL_from:
			case LITERAL_import:
			case LITERAL_constructor:
			case LITERAL_ctor:
			case LITERAL_destructor:
			case LITERAL_dtor:
			case LITERAL_def:
			case LITERAL_var:
			case LITERAL_val:
			case LITERAL_alias:
			case SEMI:
			case LITERAL_invariant:
			case LITERAL_access:
			case LITERAL_prop:
			case LITERAL_property: {
				opt_semi();
				if (inputState.guessing == 0) {
					fb = new FunctionBodyEmptyImpl();
				}
				break;
			}
			default: {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_23);
			} else {
				throw ex;
			}
		}
		return fb;
	}

	public final Scope3 functionScope(
			FunctionDef parent
									 ) throws RecognitionException, TokenStreamException {
		Scope3 sc;

		sc = new Scope3Impl(parent);
		ClassStatement cls = null;

		try {      // for error handling
			match(LCURLY);
			docstrings(sc);
			{
				switch (LA(1)) {
				case IDENT:
				case STRING_LITERAL:
				case CHAR_LITERAL:
				case NUM_INT:
				case NUM_FLOAT:
				case LITERAL_extend:
				case LITERAL_class:
				case LBRACK:
				case LITERAL_const:
				case LPAREN:
				case LCURLY:
				case RCURLY:
				case LITERAL_continue:
				case LITERAL_break:
				case LITERAL_return:
				case LITERAL_var:
				case LITERAL_val:
				case LITERAL_yield:
				case LITERAL_construct:
				case PLUS:
				case MINUS:
				case INC:
				case DEC:
				case BNOT:
				case LNOT:
				case LITERAL_true:
				case LITERAL_false:
				case LITERAL_this:
				case LITERAL_null:
				case LITERAL_function:
				case LITERAL_procedure:
				case LITERAL_if:
				case LITERAL_match:
				case LITERAL_case:
				case LITERAL_while:
				case LITERAL_do:
				case LITERAL_iterate: {
					{
						_loop93:
						do {
							if ((_tokenSet_41.member(LA(1)))) {
								{
									switch (LA(1)) {
									case LITERAL_extend:
									case LITERAL_class: {
										cls = classStatement(sc.getParent(), cur, null/*annotations*/);
										if (inputState.guessing == 0) {
											sc.add(cls);
										}
										break;
									}
									case LITERAL_continue: {
										match(LITERAL_continue);
										break;
									}
									case LITERAL_break: {
										match(LITERAL_break);
										break;
									}
									case LITERAL_return: {
										match(LITERAL_return);
										{
											boolean synPredMatched91 = false;
											if (((_tokenSet_18.member(LA(1))) && (_tokenSet_42.member(LA(2))))) {
												int _m91 = mark();
												synPredMatched91 = true;
												inputState.guessing++;
												try {
													{
														expression();
													}
												} catch (RecognitionException pe) {
													synPredMatched91 = false;
												}
												rewind(_m91);
												inputState.guessing--;
											}
											if (synPredMatched91) {
												{
													expr = expression();
												}
											} else if ((_tokenSet_43.member(LA(1))) && (_tokenSet_44.member(LA(2)))) {
											} else {
												throw new NoViableAltException(LT(1), getFilename());
											}

										}
										break;
									}
									default:
										if ((_tokenSet_35.member(LA(1))) && (_tokenSet_45.member(LA(2)))) {
											statement(sc.statementClosure(), sc.getParent());
										} else if ((_tokenSet_18.member(LA(1))) && (_tokenSet_42.member(LA(2)))) {
											expr = expression();
											if (inputState.guessing == 0) {
												sc.statementWrapper(expr);
											}
										} else {
											throw new NoViableAltException(LT(1), getFilename());
										}
									}
								}
								opt_semi();
							} else {
								break _loop93;
							}

						} while (true);
					}
					break;
				}
				case LITERAL_abstract: {
					match(LITERAL_abstract);
					opt_semi();
					if (inputState.guessing == 0) {
						parent.setAbstract(true);
					}
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			match(RCURLY);
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_0);
			} else {
				throw ex;
			}
		}
		return sc;
	}

	public final FuncTypeName functionTypeName2_function() throws RecognitionException, TokenStreamException {
		FuncTypeName tn;

		tn = new FuncTypeNameImpl(cur);
		TypeName      rtn = null;
		TypeNameList  tnl = null;
		FormalArgList op  = null;

		try {      // for error handling
			{
				switch (LA(1)) {
				case LITERAL_function: {
					match(LITERAL_function);
					break;
				}
				case LITERAL_func: {
					match(LITERAL_func);
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			if (inputState.guessing == 0) {
				tn.type(TypeModifiers.FUNCTION);
			}
			{
				match(LPAREN);
				{
					boolean synPredMatched279 = false;
					if (((_tokenSet_61.member(LA(1))) && (_tokenSet_66.member(LA(2))))) {
						int _m279 = mark();
						synPredMatched279 = true;
						inputState.guessing++;
						try {
							{
								typeNameList2();
							}
						} catch (RecognitionException pe) {
							synPredMatched279 = false;
						}
						rewind(_m279);
						inputState.guessing--;
					}
					if (synPredMatched279) {
						tnl = typeNameList2();
					} else if ((_tokenSet_67.member(LA(1))) && (_tokenSet_68.member(LA(2)))) {
						op = formalArgList();
					} else {
						throw new NoViableAltException(LT(1), getFilename());
					}

				}
				match(RPAREN);
			}
			if (inputState.guessing == 0) {
				if (tnl != null) tn.argList(tnl);
				else tn.argList(op);
			}
			{
				if ((LA(1) == TOK_COLON || LA(1) == TOK_ARROW) && (_tokenSet_61.member(LA(2)))) {
					{
						switch (LA(1)) {
						case TOK_ARROW: {
							match(TOK_ARROW);
							break;
						}
						case TOK_COLON: {
							match(TOK_COLON);
							break;
						}
						default: {
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
					}
					rtn = typeName2();
					if (inputState.guessing == 0) {
						tn.returnValue(rtn);
					}
				} else if ((_tokenSet_7.member(LA(1))) && (_tokenSet_65.member(LA(2)))) {
				} else {
					throw new NoViableAltException(LT(1), getFilename());
				}

			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return tn;
	}

	public final TypeNameList typeNameList2() throws RecognitionException, TokenStreamException {
		TypeNameList cr;

		TypeName tn = null;
		cr = new TypeNameListImpl();

		try {      // for error handling
			tn = typeName2();
			if (inputState.guessing == 0) {
				cr.add(tn);
			}
			{
				_loop295:
				do {
					if ((LA(1) == COMMA)) {
						match(COMMA);
						tn = typeName2();
						if (inputState.guessing == 0) {
							cr.add(tn);
						}
					} else {
						break _loop295;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_11);
			} else {
				throw ex;
			}
		}
		return cr;
	}

	public final FormalArgList formalArgList() throws RecognitionException, TokenStreamException {
		FormalArgList fal;

		fal = new FormalArgListImpl();

		try {      // for error handling
			formalArgList_(fal);
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_54);
			} else {
				throw ex;
			}
		}
		return fal;
	}

	public final @Nullable FuncTypeName functionTypeName2() throws RecognitionException, TokenStreamException {
		FuncTypeName tn;

		tn = null;

		try {      // for error handling
			switch (LA(1)) {
			case LITERAL_function:
			case LITERAL_func: {
				tn = functionTypeName2_function();
				break;
			}
			case LITERAL_procedure:
			case LITERAL_proc: {
				tn = functionTypeName2_procedure();
				break;
			}
			default: {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return tn;
	}

	public final void formalArgList_(
			FormalArgList fal
									) throws RecognitionException, TokenStreamException {


		try {      // for error handling
			{
				switch (LA(1)) {
				case IDENT:
				case LITERAL_const:
				case LITERAL_in:
				case LITERAL_out:
				case LITERAL_ref: {
					formalArgListItem_priv(fal.next());
					{
						_loop302:
						do {
							if ((LA(1) == COMMA)) {
								match(COMMA);
								formalArgListItem_priv(fal.next());
							} else {
								break _loop302;
							}

						} while (true);
					}
					break;
				}
				case RPAREN: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_54);
			} else {
				throw ex;
			}
		}
	}

	public final void formalArgListItem_priv(
			FormalArgListItem fali
											) throws RecognitionException, TokenStreamException {

		TypeName        tn = null;
		IdentExpression i  = null;

		try {      // for error handling
			{
				{
					if ((_tokenSet_70.member(LA(1))) && (_tokenSet_71.member(LA(2)))) {
						regularQualifiers2((NormalTypeName) fali.typeName());
					} else if ((LA(1) == IDENT) && (LA(2) == TOK_COLON || LA(2) == RPAREN || LA(2) == COMMA)) {
					} else {
						throw new NoViableAltException(LT(1), getFilename());
					}

				}
				i = ident();
				if (inputState.guessing == 0) {
					fali.setName(i);
				}
				{
					switch (LA(1)) {
					case TOK_COLON: {
						match(TOK_COLON);
						tn = typeName2();
						if (inputState.guessing == 0) {
							fali.setTypeName(tn);
						}
						break;
					}
					case RPAREN:
					case COMMA: {
						break;
					}
					default: {
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
				}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_72);
			} else {
				throw ex;
			}
		}
	}

	public final void regularQualifiers2(
			NormalTypeName fp
										) throws RecognitionException, TokenStreamException {


		try {      // for error handling
			{
				switch (LA(1)) {
				case LITERAL_in: {
					match(LITERAL_in);
					if (inputState.guessing == 0) {
						fp.setIn(true);
					}
					break;
				}
				case LITERAL_out: {
					match(LITERAL_out);
					if (inputState.guessing == 0) {
						fp.setOut(true);
					}
					break;
				}
				case IDENT:
				case LITERAL_const:
				case LITERAL_ref: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			{
				switch (LA(1)) {
				case LITERAL_const: {
					{
						match(LITERAL_const);
						if (inputState.guessing == 0) {
							fp.setConstant(true);
						}
						{
							switch (LA(1)) {
							case LITERAL_ref: {
								match(LITERAL_ref);
								if (inputState.guessing == 0) {
									fp.setReference(true);
								}
								break;
							}
							case IDENT: {
								break;
							}
							default: {
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
						}
					}
					break;
				}
				case LITERAL_ref: {
					match(LITERAL_ref);
					if (inputState.guessing == 0) {
						fp.setReference(true);
					}
					break;
				}
				case IDENT: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_10);
			} else {
				throw ex;
			}
		}
	}

	public final FuncTypeName functionTypeName2_procedure() throws RecognitionException, TokenStreamException {
		FuncTypeName tn;

		tn = new FuncTypeNameImpl(cur);
		TypeNameList  tnl = null;
		FormalArgList op  = null;

		try {      // for error handling
			{
				switch (LA(1)) {
				case LITERAL_procedure: {
					match(LITERAL_procedure);
					break;
				}
				case LITERAL_proc: {
					match(LITERAL_proc);
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			if (inputState.guessing == 0) {
				tn.type(TypeModifiers.PROCEDURE);
			}
			{
				match(LPAREN);
				{
					boolean synPredMatched287 = false;
					if (((_tokenSet_61.member(LA(1))) && (_tokenSet_66.member(LA(2))))) {
						int _m287 = mark();
						synPredMatched287 = true;
						inputState.guessing++;
						try {
							{
								typeNameList2();
							}
						} catch (RecognitionException pe) {
							synPredMatched287 = false;
						}
						rewind(_m287);
						inputState.guessing--;
					}
					if (synPredMatched287) {
						tnl = typeNameList2();
					} else if ((_tokenSet_67.member(LA(1))) && (_tokenSet_69.member(LA(2)))) {
						op = formalArgList();
					} else {
						throw new NoViableAltException(LT(1), getFilename());
					}

				}
				match(RPAREN);
			}
			if (inputState.guessing == 0) {
				if (tnl != null) tn.argList(tnl);
				else tn.argList(op);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return tn;
	}

	public final GenericTypeName genericTypeName2() throws RecognitionException, TokenStreamException {
		GenericTypeName tn;

		tn = new GenericTypeNameImpl(cur);
		TypeName tn2 = null;

		try {      // for error handling
			{
				switch (LA(1)) {
				case LITERAL_generic: {
					match(LITERAL_generic);
					break;
				}
				case QUESTION: {
					match(QUESTION);
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			xy = qualident();
			if (inputState.guessing == 0) {
				tn.typeName(xy);
				tn.set(TypeModifiers.GENERIC);
			}
			{
				if ((LA(1) == LT_) && (_tokenSet_61.member(LA(2)))) {
					match(LT_);
					tn2 = typeName2();
					if (inputState.guessing == 0) {
						tn.setConstraint(tn2);
					}
				} else if ((_tokenSet_7.member(LA(1))) && (_tokenSet_65.member(LA(2)))) {
				} else {
					throw new NoViableAltException(LT(1), getFilename());
				}

			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return tn;
	}

	public final Qualident qualident() throws RecognitionException, TokenStreamException {
		Qualident q;

		Token d1 = null;
		q = new QualidentImpl();
		IdentExpression r1 = null, r2 = null;

		try {      // for error handling
			r1 = ident();
			if (inputState.guessing == 0) {
				q.append(r1);
			}
			{
				_loop11:
				do {
					if ((LA(1) == DOT) && (LA(2) == IDENT)) {
						d1 = LT(1);
						match(DOT);
						r2 = ident();
						if (inputState.guessing == 0) {
							q.appendDot(d1);
							q.append(r2);
						}
					} else {
						break _loop11;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_2);
			} else {
				throw ex;
			}
		}
		return q;
	}

	public final void importPart1(
			AssigningImportStatement cr
								 ) throws RecognitionException, TokenStreamException {

		IdentExpression i1 = null;
		Qualident       q1 = null;

		try {      // for error handling
			i1 = ident();
			match(BECOMES);
			q1 = qualident();
			if (inputState.guessing == 0) {
				cr.addAssigningPart(i1, q1);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_28);
			} else {
				throw ex;
			}
		}
	}

	public final void ifConditional(
			IfConditional ifex
								   ) throws RecognitionException, TokenStreamException {

		IfConditionalContext ifc_top = null, ifc = null;
		IfConditional        else_   = null;

		try {      // for error handling
			match(LITERAL_if);
			expr = expression();
			if (inputState.guessing == 0) {
				ifex.expr(expr);
				cur = ifex.getContext();
			}
			sco = scope3(ifex);
			if (inputState.guessing == 0) {
				ifex.scope(sco);
			}
			if (inputState.guessing == 0) {
				cur = cur.getParent();
			}
			{
				_loop246:
				do {
					boolean synPredMatched245 = false;
					if (((LA(1) == LITERAL_else || LA(1) == LITERAL_elseif) && (_tokenSet_57.member(LA(2))))) {
						int _m245 = mark();
						synPredMatched245 = true;
						inputState.guessing++;
						try {
							{
								match(LITERAL_else);
								match(LITERAL_if);
							}
						} catch (RecognitionException pe) {
							synPredMatched245 = false;
						}
						rewind(_m245);
						inputState.guessing--;
					}
					if (synPredMatched245) {
						elseif_part(ifex.elseif());
					} else {
						break _loop246;
					}

				} while (true);
			}
			{
				switch (LA(1)) {
				case LITERAL_else: {
					match(LITERAL_else);
					if (inputState.guessing == 0) {
						else_ = ifex.else_();
						cur   = else_.getContext();
					}
					sco = scope3(else_);
					if (inputState.guessing == 0) {
						if (else_ != null) else_.scope(sco);
					}
					if (inputState.guessing == 0) {
						cur = cur.getParent();
					}
					break;
				}
				case IDENT:
				case STRING_LITERAL:
				case CHAR_LITERAL:
				case NUM_INT:
				case NUM_FLOAT:
				case LITERAL_extend:
				case LITERAL_class:
				case LBRACK:
				case LITERAL_const:
				case LPAREN:
				case LCURLY:
				case RCURLY:
				case LITERAL_continue:
				case LITERAL_break:
				case LITERAL_return:
				case LITERAL_with:
				case LITERAL_post:
				case LITERAL_ensures:
				case LITERAL_var:
				case LITERAL_val:
				case LITERAL_yield:
				case LITERAL_construct:
				case SEMI:
				case PLUS:
				case MINUS:
				case INC:
				case DEC:
				case BNOT:
				case LNOT:
				case LITERAL_true:
				case LITERAL_false:
				case LITERAL_this:
				case LITERAL_null:
				case LITERAL_function:
				case LITERAL_procedure:
				case LITERAL_if:
				case LITERAL_match:
				case LITERAL_case:
				case LITERAL_while:
				case LITERAL_do:
				case LITERAL_iterate: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_39);
			} else {
				throw ex;
			}
		}
	}

	public final @Nullable ImportStatement importStatement(
			OS_Element el
														  ) throws RecognitionException, TokenStreamException {
		ImportStatement pc;

		pc = null;
		ImportContext ctx = null;

		try {      // for error handling
			switch (LA(1)) {
			case LITERAL_from: {
				match(LITERAL_from);
				if (inputState.guessing == 0) {
					pc  = new RootedImportStatement(el);
					ctx = new ImportContext(cur, pc);
					pc.setContext(ctx);
					cur = ctx;
				}
				xy = qualident();
				match(LITERAL_import);
				qualidentList(((RootedImportStatement) pc).importList());
				if (inputState.guessing == 0) {
					((RootedImportStatement) pc).importRoot(xy);
				}
				opt_semi();
				break;
			}
			case LITERAL_import: {
				match(LITERAL_import);
				{
					boolean synPredMatched40 = false;
					if (((LA(1) == IDENT) && (LA(2) == BECOMES))) {
						int _m40 = mark();
						synPredMatched40 = true;
						inputState.guessing++;
						try {
							{
								match(IDENT);
								match(BECOMES);
							}
						} catch (RecognitionException pe) {
							synPredMatched40 = false;
						}
						rewind(_m40);
						inputState.guessing--;
					}
					if (synPredMatched40) {
						if (inputState.guessing == 0) {
							pc  = new AssigningImportStatement(el);
							ctx = new ImportContext(cur, pc);
							pc.setContext(ctx);
							cur = ctx;
						}
						importPart1((AssigningImportStatement) pc);
						{
							_loop42:
							do {
								if ((LA(1) == COMMA)) {
									match(COMMA);
									importPart1((AssigningImportStatement) pc);
								} else {
									break _loop42;
								}

							} while (true);
						}
					} else {
						boolean synPredMatched44 = false;
						if (((LA(1) == IDENT) && (LA(2) == DOT || LA(2) == LCURLY))) {
							int _m44 = mark();
							synPredMatched44 = true;
							inputState.guessing++;
							try {
								{
									qualident();
									match(LCURLY);
								}
							} catch (RecognitionException pe) {
								synPredMatched44 = false;
							}
							rewind(_m44);
							inputState.guessing--;
						}
						if (synPredMatched44) {
							if (inputState.guessing == 0) {
								pc  = new QualifiedImportStatement(el);
								ctx = new ImportContext(cur, pc);
								pc.setContext(ctx);
								cur = ctx;
							}
							importPart2((QualifiedImportStatement) pc);
							{
								_loop46:
								do {
									if ((LA(1) == COMMA)) {
										match(COMMA);
										importPart2((QualifiedImportStatement) pc);
									} else {
										break _loop46;
									}

								} while (true);
							}
						} else if ((LA(1) == IDENT) && (_tokenSet_27.member(LA(2)))) {
							if (inputState.guessing == 0) {
								pc  = new NormalImportStatement(el);
								ctx = new ImportContext(cur, pc);
								pc.setContext(ctx);
								cur = ctx;
							}
							importPart3((NormalImportStatement) pc);
							{
								_loop48:
								do {
									if ((LA(1) == COMMA)) {
										match(COMMA);
										importPart3((NormalImportStatement) pc);
									} else {
										break _loop48;
									}

								} while (true);
							}
						} else {
							throw new NoViableAltException(LT(1), getFilename());
						}
					}
				}
				opt_semi();
				break;
			}
			default: {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
		return pc;
	}

	public final @Nullable IExpression inclusiveOrExpression() throws RecognitionException, TokenStreamException {
		IExpression ee;

		ee = null;
		IExpression e3 = null;

		try {      // for error handling
			ee = exclusiveOrExpression();
			{
				_loop190:
				do {
					if ((LA(1) == BOR) && (_tokenSet_18.member(LA(2)))) {
						match(BOR);
						e3 = exclusiveOrExpression();
						if (inputState.guessing == 0) {
							ee = ExpressionBuilder.build(ee, ExpressionKind.BOR, e3);
						}
					} else {
						break _loop190;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable IndexingStatement indexingStatement() throws RecognitionException, TokenStreamException {
		IndexingStatement idx;

		Token          i1 = null;
		ExpressionList el = null;
		idx = null;
		IndexingItem item;

		try {      // for error handling
			match(LITERAL_indexing);
			if (inputState.guessing == 0) {
				idx = new IndexingStatementImpl(out.module());
			}
			{
				_loop7:
				do {
					if ((LA(1) == IDENT)) {
						i1 = LT(1);
						match(IDENT);
						match(TOK_COLON);
						el = expressionList();
						if (inputState.guessing == 0) {
							item = new IndexingItemImpl(i1, el);
							idx.add(item);
						}
					} else {
						break _loop7;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_1);
			} else {
				throw ex;
			}
		}
		return idx;
	}

	public final @Nullable TypeName inhTypeName() throws RecognitionException, TokenStreamException {
		TypeName tn;

		tn = null;

		try {      // for error handling
			{
				switch (LA(1)) {
				case LITERAL_typeof: {
					tn = typeOfTypeName2();
					break;
				}
				case IDENT:
				case LITERAL_const:
				case LITERAL_in:
				case LITERAL_out:
				case LITERAL_ref: {
					tn = normalTypeName2();
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			if (inputState.guessing == 0) {
				tn.setContext(cur);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_29);
			} else {
				throw ex;
			}
		}
		return tn;
	}

	public final void invariantStatement(
			InvariantStatement cr
										) throws RecognitionException, TokenStreamException {

		Token                  i1  = null;
		InvariantStatementPart isp = null;

		try {      // for error handling
			match(LITERAL_invariant);
			{
				_loop173:
				do {
					if ((_tokenSet_18.member(LA(1)))) {
						if (inputState.guessing == 0) {
							isp = new InvariantStatementPartImpl(cr, i1);
						}
						{
							if ((LA(1) == IDENT) && (LA(2) == TOK_COLON)) {
								i1 = LT(1);
								match(IDENT);
								match(TOK_COLON);
							} else if ((_tokenSet_18.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
							} else {
								throw new NoViableAltException(LT(1), getFilename());
							}

						}
						expr = expression();
						if (inputState.guessing == 0) {
							isp.setExpr(expr);
						}
					} else {
						break _loop173;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_17);
			} else {
				throw ex;
			}
		}
	}

	public final @Nullable IExpression logicalAndExpression() throws RecognitionException, TokenStreamException {
		IExpression ee;

		ee = null;
		IExpression e3 = null;

		try {      // for error handling
			ee = inclusiveOrExpression();
			{
				_loop187:
				do {
					if ((LA(1) == LAND) && (_tokenSet_18.member(LA(2)))) {
						match(LAND);
						e3 = inclusiveOrExpression();
						if (inputState.guessing == 0) {
							ee = ExpressionBuilder.build(ee, ExpressionKind.LAND, e3);
						}
					} else {
						break _loop187;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable IExpression logicalOrExpression() throws RecognitionException, TokenStreamException {
		IExpression ee;

		ee = null;
		IExpression e3 = null;

		try {      // for error handling
			ee = logicalAndExpression();
			{
				_loop184:
				do {
					if ((LA(1) == LOR) && (_tokenSet_18.member(LA(2)))) {
						match(LOR);
						e3 = logicalAndExpression();
						if (inputState.guessing == 0) {
							ee = ExpressionBuilder.build(ee, ExpressionKind.LOR, e3);
						}
					} else {
						break _loop184;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable IExpression multiplicativeExpression() throws RecognitionException, TokenStreamException {
		IExpression ee;

		ee = null;
		IExpression    e3 = null;
		ExpressionKind e2 = null;

		try {      // for error handling
			ee = unaryExpression();
			{
				_loop217:
				do {
					if (((LA(1) >= STAR && LA(1) <= MOD)) && (_tokenSet_18.member(LA(2)))) {
						{
							switch (LA(1)) {
							case STAR: {
								match(STAR);
								if (inputState.guessing == 0) {
									e2 = ExpressionKind.MULTIPLY;
								}
								break;
							}
							case DIV: {
								match(DIV);
								if (inputState.guessing == 0) {
									e2 = ExpressionKind.DIVIDE;
								}
								break;
							}
							case MOD: {
								match(MOD);
								if (inputState.guessing == 0) {
									e2 = ExpressionKind.MODULO;
								}
								break;
							}
							default: {
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
						}
						e3 = unaryExpression();
						if (inputState.guessing == 0) {
							ee = ExpressionBuilder.build(ee, e2, e3);
						}
					} else {
						break _loop217;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final void importPart2(
			QualifiedImportStatement cr
								 ) throws RecognitionException, TokenStreamException {

		Qualident q3;
		IdentList il = new IdentListImpl();

		try {      // for error handling
			q3 = qualident();
			match(LCURLY);
			il = identList2();
			if (inputState.guessing == 0) {
				cr.addSelectivePart(q3, il);
			}
			match(RCURLY);
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_28);
			} else {
				throw ex;
			}
		}
	}

	public final IdentList identList2() throws RecognitionException, TokenStreamException {
		IdentList ail;

		IdentExpression s = null;
		ail = new IdentListImpl();

		try {      // for error handling
			s = ident();
			if (inputState.guessing == 0) {
				ail.push(s);
			}
			{
				_loop157:
				do {
					if ((LA(1) == COMMA)) {
						match(COMMA);
						s = ident();
						if (inputState.guessing == 0) {
							ail.push(s);
						}
					} else {
						break _loop157;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_17);
			} else {
				throw ex;
			}
		}
		return ail;
	}

	public final void importPart3(
			NormalImportStatement cr
								 ) throws RecognitionException, TokenStreamException {

		Qualident q2;

		try {      // for error handling
			q2 = qualident();
			if (inputState.guessing == 0) {
				cr.addNormalPart(q2);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_28);
			} else {
				throw ex;
			}
		}
	}

	public final void matchConditional(
			MatchConditional mc, OS_Element aParent
									  ) throws RecognitionException, TokenStreamException {

		MatchConditionalImpl.MatchArm_TypeMatch    mcp1 = null;
		MatchConditionalImpl.MatchConditionalPart2 mcp2 = null;
		MatchConditionalImpl.MatchConditionalPart3 mcp3 = null;
		TypeName                                   tn   = null;
		IdentExpression                            i1   = null;
		MatchContext                               ctx  = null;

		try {      // for error handling
			match(LITERAL_match);
			expr = expression();
			if (inputState.guessing == 0) {
				/*mc.setParent(aParent);*/
				mc.expr(expr);
			}
			match(LCURLY);
			{
				int _cnt252 = 0;
				_loop252:
				do {
					if ((LA(1) == IDENT) && (LA(2) == TOK_COLON)) {
						if (inputState.guessing == 0) {
							mcp1 = mc.typeMatch();
						}
						i1 = ident();
						if (inputState.guessing == 0) {
							mcp1.ident(i1);
						}
						match(TOK_COLON);
						tn = typeName2();
						if (inputState.guessing == 0) {
							mcp1.setTypeName(tn);
						}
						sco = scope3(mcp1);
						if (inputState.guessing == 0) {
							mcp1.scope(sco);
						}
					} else if ((_tokenSet_18.member(LA(1))) && (_tokenSet_58.member(LA(2)))) {
						if (inputState.guessing == 0) {
							mcp2 = mc.normal();
						}
						expr = expression();
						if (inputState.guessing == 0) {
							mcp2.expr(expr);
						}
						sco = scope3(mcp2);
						if (inputState.guessing == 0) {
							mcp2.scope(sco);
						}
					} else if ((LA(1) == LITERAL_val)) {
						if (inputState.guessing == 0) {
							mcp3 = mc.valNormal();
						}
						match(LITERAL_val);
						i1 = ident();
						if (inputState.guessing == 0) {
							mcp3.expr(i1);
						}
						sco = scope3(mcp3);
						if (inputState.guessing == 0) {
							mcp3.scope(sco);
						}
					} else {
						if (_cnt252 >= 1) {
							break _loop252;
						} else {
							throw new NoViableAltException(LT(1), getFilename());
						}
					}

					_cnt252++;
				} while (true);
			}
			match(RCURLY);
			if (inputState.guessing == 0) {
				mc.postConstruct();
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_39);
			} else {
				throw ex;
			}
		}
	}

	public final void namespaceScope(
			NamespaceStatement cr
									) throws RecognitionException, TokenStreamException {

		AccessNotation         acs = null;
		TypeAliasStatement     tal = null;
		BaseFunctionDef        fd  = null;
		List<AnnotationClause> as  = new ArrayList<AnnotationClause>();
		AnnotationClause       a   = null;

		try {      // for error handling
			docstrings(cr);
			{
				_loop72:
				do {
					if ((_tokenSet_26.member(LA(1)))) {
						{
							switch (LA(1)) {
							case LITERAL_const:
							case LITERAL_var:
							case LITERAL_val: {
								varStmt(cr.statementClosure(), cr);
								break;
							}
							case LITERAL_type: {
								tal = typeAlias(cr);
								if (inputState.guessing == 0) {
									cr.addToContainer(tal);
								}
								break;
							}
							case LITERAL_access: {
								acs = accessNotation();
								if (inputState.guessing == 0) {
									cr.addAccess(acs);
								}
								break;
							}
							default:
								if ((LA(1) == IDENT || LA(1) == ANNOT || LA(1) == LITERAL_def) && (_tokenSet_14.member(LA(2)))) {
									{
										_loop71:
										do {
											if ((LA(1) == ANNOT)) {
												a = annotation_clause();
												if (inputState.guessing == 0) {
													as.add(a);
												}
											} else {
												break _loop71;
											}

										} while (true);
									}
									fd = function_definition(cr, cr.getContext(), as);
								} else if ((_tokenSet_15.member(LA(1))) && (_tokenSet_16.member(LA(2)))) {
									programStatement(/*cr.XXX(),*/ cr);
								} else {
									throw new NoViableAltException(LT(1), getFilename());
								}
							}
						}
						opt_semi();
					} else {
						break _loop72;
					}

				} while (true);
			}
			{
				switch (LA(1)) {
				case LITERAL_invariant: {
					invariantStatement(cr.invariantStatement());
					break;
				}
				case RCURLY: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_17);
			} else {
				throw ex;
			}
		}
	}

	public final @Nullable FormalArgList opfal() throws RecognitionException, TokenStreamException {
		FormalArgList fal;

		fal = null;

		try {      // for error handling
			match(LPAREN);
			fal = formalArgList();
			match(RPAREN);
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_30);
			} else {
				throw ex;
			}
		}
		return fal;
	}

	public final void opt_semi() throws RecognitionException, TokenStreamException {


		try {      // for error handling
			{
				if ((LA(1) == SEMI) && (_tokenSet_3.member(LA(2)))) {
					match(SEMI);
				} else if ((_tokenSet_3.member(LA(1))) && (_tokenSet_4.member(LA(2)))) {
				} else {
					throw new NoViableAltException(LT(1), getFilename());
				}

			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_3);
			} else {
				throw ex;
			}
		}
	}

	public final void namespaceStatement(
			NamespaceStatement cls, List<AnnotationClause> as
										) throws RecognitionException, TokenStreamException {

		AnnotationClause a   = null;
		NamespaceContext ctx = null;
		IdentExpression  i1  = null;

		try {      // for error handling
			if (inputState.guessing == 0) {
				cls.addAnnotations(as);
			}
			match(LITERAL_namespace);
			{
				if ((LA(1) == IDENT)) {
					i1 = ident();
					if (inputState.guessing == 0) {
						cls.setName(i1);
					}
				} else if ((LA(1) == LCURLY) && (_tokenSet_25.member(LA(2)))) {
					if (inputState.guessing == 0) {
						cls.setType(NamespaceTypes.MODULE);
					}
				} else if ((LA(1) == LCURLY) && (_tokenSet_25.member(LA(2)))) {
				} else {
					throw new NoViableAltException(LT(1), getFilename());
				}

			}
			match(LCURLY);
			if (inputState.guessing == 0) {
				ctx = new NamespaceContext(cur, cls);
				cls.setContext(ctx);
				cur = ctx;
			}
			namespaceScope(cls);
			match(RCURLY);
			if (inputState.guessing == 0) {
				cls.postConstruct();
				cur = ctx.getParent();
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
	}

	public final @Nullable FunctionDef normal_function_definition(
			OS_Element parent, Context ctx, List<AnnotationClause> as
																 ) throws RecognitionException, TokenStreamException {
		FunctionDef fd;

		fd = null;
		FunctionHeader fh = null;
		FunctionBody   fb = null;

		try {      // for error handling
			fh = function_header();
			if (inputState.guessing == 0) {
				fd  = new FunctionDefImpl(parent, ctx);
				cur = fd.getContext();
			}
			fb = function_body(fd);
			if (inputState.guessing == 0) {
				fd.setAnnotations(as);
				fd.setHeader(fh);
				fd.setBody(fb);
				cur = ctx.getParent();
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_23);
			} else {
				throw ex;
			}
		}
		return fd;
	}

	public final NormalTypeName normalTypeName2() throws RecognitionException, TokenStreamException {
		NormalTypeName tn;

		tn = new RegularTypeNameImpl(cur);
		TypeNameList rtn = null;

		try {      // for error handling
			regularQualifiers2(tn);
			xy = qualident();
			if (inputState.guessing == 0) {
				tn.setName(xy);
			}
			{
				if ((LA(1) == LBRACK) && (_tokenSet_61.member(LA(2)))) {
					match(LBRACK);
					rtn = typeNameList2();
					if (inputState.guessing == 0) {
						tn.addGenericPart(rtn);
					}
					match(RBRACK);
				} else if ((_tokenSet_2.member(LA(1))) && (_tokenSet_65.member(LA(2)))) {
				} else {
					throw new NoViableAltException(LT(1), getFilename());
				}

			}
			{
				switch (LA(1)) {
				case QUESTION: {
					match(QUESTION);
					if (inputState.guessing == 0) {
						tn.setNullable();
					}
					break;
				}
				case EOF:
				case AS:
				case CAST_TO:
				case LITERAL_package:
				case IDENT:
				case TOK_COLON:
				case STRING_LITERAL:
				case CHAR_LITERAL:
				case NUM_INT:
				case NUM_FLOAT:
				case DOT:
				case LITERAL_extend:
				case LITERAL_class:
				case LBRACK:
				case RBRACK:
				case LITERAL_const:
				case LPAREN:
				case RPAREN:
				case LCURLY:
				case RCURLY:
				case LITERAL_type:
				case BECOMES:
				case BOR:
				case ANNOT:
				case LITERAL_namespace:
				case LITERAL_from:
				case LITERAL_import:
				case COMMA:
				case LT_:
				case LITERAL_constructor:
				case LITERAL_ctor:
				case LITERAL_destructor:
				case LITERAL_dtor:
				case LITERAL_continue:
				case LITERAL_break:
				case LITERAL_return:
				case LITERAL_with:
				case LITERAL_post:
				case LITERAL_ensures:
				case LITERAL_def:
				case LITERAL_var:
				case LITERAL_val:
				case LITERAL_alias:
				case LITERAL_yield:
				case LITERAL_construct:
				case SEMI:
				case LITERAL_invariant:
				case LITERAL_access:
				case EQUAL:
				case PLUS_ASSIGN:
				case MINUS_ASSIGN:
				case STAR_ASSIGN:
				case DIV_ASSIGN:
				case MOD_ASSIGN:
				case SR_ASSIGN:
				case BSR_ASSIGN:
				case SL_ASSIGN:
				case BAND_ASSIGN:
				case BXOR_ASSIGN:
				case BOR_ASSIGN:
				case LOR:
				case LAND:
				case BXOR:
				case BAND:
				case NOT_EQUAL:
				case GT:
				case LE:
				case GE:
				case LITERAL_is_a:
				case SL:
				case SR:
				case BSR:
				case PLUS:
				case MINUS:
				case STAR:
				case DIV:
				case MOD:
				case INC:
				case DEC:
				case BNOT:
				case LNOT:
				case LITERAL_true:
				case LITERAL_false:
				case LITERAL_this:
				case LITERAL_null:
				case LITERAL_function:
				case LITERAL_procedure:
				case LITERAL_if:
				case LITERAL_match:
				case LITERAL_case:
				case LITERAL_while:
				case LITERAL_do:
				case LITERAL_iterate:
				case LITERAL_to:
				case LITERAL_prop:
				case LITERAL_property: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return tn;
	}

	public final @Nullable IExpression postfixExpression() throws RecognitionException, TokenStreamException {
		IExpression ee;

		Token lb = null;
		Token rb = null;
		Token lp = null;
		Token in = null;
		Token de = null;
		ee = null;
		TypeCastExpression tc = null;
		TypeName           tn = null;
		IExpression        e3 = null;
		ExpressionList     el = null;

		try {      // for error handling
			ee = primaryExpression();
			{
				_loop225:
				do {
					if ((LA(1) == DOT) && (LA(2) == IDENT)) {
						match(DOT);
						{
							ee = dot_expression_or_procedure_call(ee);
						}
					} else if ((LA(1) == LBRACK) && (_tokenSet_18.member(LA(2)))) {
						lb = LT(1);
						match(LBRACK);
						expr = expression();
						rb   = LT(1);
						match(RBRACK);
						if (inputState.guessing == 0) {
							ee = new GetItemExpressionImpl(ee, expr);
							((GetItemExpression) ee).parens(lb, rb);
						}
						{
							if ((LA(1) == BECOMES) && (_tokenSet_18.member(LA(2)))) {
								match(BECOMES);
								expr = expression();
								if (inputState.guessing == 0) {
									ee = new SetItemExpressionImpl((GetItemExpression) ee, expr);
								}
							} else if ((_tokenSet_7.member(LA(1))) && (_tokenSet_56.member(LA(2)))) {
							} else {
								throw new NoViableAltException(LT(1), getFilename());
							}

						}
					} else if ((LA(1) == LPAREN) && (_tokenSet_59.member(LA(2)))) {
						lp = LT(1);
						match(LPAREN);
						{
							switch (LA(1)) {
							case IDENT:
							case STRING_LITERAL:
							case CHAR_LITERAL:
							case NUM_INT:
							case NUM_FLOAT:
							case LBRACK:
							case LPAREN:
							case LCURLY:
							case PLUS:
							case MINUS:
							case INC:
							case DEC:
							case BNOT:
							case LNOT:
							case LITERAL_true:
							case LITERAL_false:
							case LITERAL_this:
							case LITERAL_null:
							case LITERAL_function:
							case LITERAL_procedure: {
								el = expressionList();
								break;
							}
							case RPAREN: {
								break;
							}
							default: {
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
						}
						if (inputState.guessing == 0) {
							ProcedureCallExpression pce = new ProcedureCallExpressionImpl();
							pce.identifier(ee);
							pce.setArgs(el);
							ee = pce;
						}
						match(RPAREN);
					} else {
						break _loop225;
					}

				} while (true);
			}
			{
				if ((LA(1) == INC) && (_tokenSet_7.member(LA(2)))) {
					in = LT(1);
					match(INC);
					if (inputState.guessing == 0) {
						ee.setKind(ExpressionKind.POST_INCREMENT);
					}
				} else if ((LA(1) == DEC) && (_tokenSet_7.member(LA(2)))) {
					de = LT(1);
					match(DEC);
					if (inputState.guessing == 0) {
						ee.setKind(ExpressionKind.POST_DECREMENT);
					}
				} else if ((_tokenSet_7.member(LA(1))) && (_tokenSet_56.member(LA(2)))) {
				} else {
					throw new NoViableAltException(LT(1), getFilename());
				}

			}
			{
				if ((LA(1) == AS || LA(1) == CAST_TO) && (_tokenSet_61.member(LA(2)))) {
					if (inputState.guessing == 0) {
						tc = new TypeCastExpressionImpl();
						ee = tc;
					}
					{
						switch (LA(1)) {
						case AS: {
							match(AS);
							if (inputState.guessing == 0) {
								tc.setKind(ExpressionKind.AS_CAST);
							}
							break;
						}
						case CAST_TO: {
							match(CAST_TO);
							if (inputState.guessing == 0) {
								tc.setKind(ExpressionKind.CAST_TO);
							}
							break;
						}
						default: {
							throw new NoViableAltException(LT(1), getFilename());
						}
						}
					}
					tn = typeName2();
					if (inputState.guessing == 0) {
						tc.setTypeName(tn);
					}
				} else if ((_tokenSet_7.member(LA(1))) && (_tokenSet_56.member(LA(2)))) {
				} else {
					throw new NoViableAltException(LT(1), getFilename());
				}

			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final Postcondition postcondition() throws RecognitionException, TokenStreamException {
		Postcondition postc;

		postc = new PostconditionImpl();
		IdentExpression id = null;

		try {      // for error handling
			{
				if ((LA(1) == IDENT) && (LA(2) == TOK_COLON)) {
					id = ident();
					match(TOK_COLON);
					if (inputState.guessing == 0) {
						postc.id(id);
					}
				} else if ((_tokenSet_18.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
				} else {
					throw new NoViableAltException(LT(1), getFilename());
				}

			}
			expr = expression();
			if (inputState.guessing == 0) {
				postc.expr(expr);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_47);
			} else {
				throw ex;
			}
		}
		return postc;
	}

	public final void program() throws RecognitionException, TokenStreamException {

		ParserClosure pc   = out.closure();
		ModuleContext mctx = new ModuleContext(out.module());
		out.module().setContext(mctx);
		cur = mctx;
		IndexingStatement idx = null;
		OS_Package        pkg;

		try {      // for error handling
			{
				switch (LA(1)) {
				case LITERAL_indexing: {
					idx = indexingStatement();
					if (inputState.guessing == 0) {
						out.module().setIndexingStatement(idx);
					}
					break;
				}
				case EOF:
				case LITERAL_package:
				case LITERAL_extend:
				case LITERAL_class:
				case ANNOT:
				case LITERAL_namespace:
				case LITERAL_from:
				case LITERAL_import:
				case LITERAL_alias: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			{
				_loop4:
				do {
					switch (LA(1)) {
					case LITERAL_package: {
						match(LITERAL_package);
						xy = qualident();
						opt_semi();
						if (inputState.guessing == 0) {
							pkg = pc.defaultPackageName(xy);
							cur = new PackageContext(cur, pkg);
							pkg.setContext((PackageContext) cur);
						}
						break;
					}
					case LITERAL_extend:
					case LITERAL_class:
					case ANNOT:
					case LITERAL_namespace:
					case LITERAL_from:
					case LITERAL_import:
					case LITERAL_alias: {
						programStatement(/*pc,*/ out.module());
						opt_semi();
						break;
					}
					default: {
						break _loop4;
					}
					}
				} while (true);
			}
			match(Token.EOF_TYPE);
			if (inputState.guessing == 0) {
				out.module().postConstruct();
				out.FinishModule();
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_0);
			} else {
				throw ex;
			}
		}
	}

	public final @Nullable IExpression primaryExpression() throws RecognitionException, TokenStreamException {
		IExpression ee;

		ee = null;
		FuncExpr        ppc = null;
		IdentExpression e   = null;
		ExpressionList  el  = null;

		try {      // for error handling
			switch (LA(1)) {
			case IDENT: {
				ee = ident();
				break;
			}
			case STRING_LITERAL:
			case CHAR_LITERAL:
			case NUM_INT:
			case NUM_FLOAT: {
				ee = constantValue();
				break;
			}
			case LITERAL_true: {
				match(LITERAL_true);
				break;
			}
			case LITERAL_false: {
				match(LITERAL_false);
				break;
			}
			case LITERAL_this: {
				match(LITERAL_this);
				break;
			}
			case LITERAL_null: {
				match(LITERAL_null);
				break;
			}
			case LPAREN: {
				match(LPAREN);
				ee = assignmentExpression();
				match(RPAREN);
				if (inputState.guessing == 0) {
					ee = new SubExpressionImpl(ee);
				}
				break;
			}
			case LCURLY:
			case LITERAL_function:
			case LITERAL_procedure: {
				if (inputState.guessing == 0) {
					ppc = new FuncExprImpl();
				}
				funcExpr(ppc);
				if (inputState.guessing == 0) {
					ee = ppc;
				}
				break;
			}
			case LBRACK: {
				match(LBRACK);
				if (inputState.guessing == 0) {
					ee = new ListExpressionImpl();
					el = new ExpressionListImpl();
				}
				el = expressionList();
				if (inputState.guessing == 0) {
					((ListExpression) ee).setContents(el);
				}
				match(RBRACK);
				break;
			}
			default: {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final void postConditionSegment(
			FunctionBody sc
										  ) throws RecognitionException, TokenStreamException {

		Postcondition po = null;

		try {      // for error handling
			{
				switch (LA(1)) {
				case LITERAL_post: {
					match(LITERAL_post);
					break;
				}
				case LITERAL_ensures: {
					match(LITERAL_ensures);
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			match(LCURLY);
			{
				_loop101:
				do {
					if ((_tokenSet_18.member(LA(1)))) {
						po = postcondition();
						if (inputState.guessing == 0) {
							sc.addPostCondition(po);
						}
					} else {
						break _loop101;
					}

				} while (true);
			}
			{
				match(RCURLY);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_17);
			} else {
				throw ex;
			}
		}
	}

	public final Precondition precondition() throws RecognitionException, TokenStreamException {
		Precondition prec;

		prec = new PreconditionImpl();
		IdentExpression id = null;

		try {      // for error handling
			{
				if ((LA(1) == IDENT) && (LA(2) == TOK_COLON)) {
					id = ident();
					match(TOK_COLON);
					if (inputState.guessing == 0) {
						prec.id(id);
					}
				} else if ((_tokenSet_18.member(LA(1))) && (_tokenSet_19.member(LA(2)))) {
				} else {
					throw new NoViableAltException(LT(1), getFilename());
				}

			}
			expr = expression();
			if (inputState.guessing == 0) {
				prec.expr(expr);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_47);
			} else {
				throw ex;
			}
		}
		return prec;
	}

	public final void preConditionSegment(
			FunctionBody sc
										 ) throws RecognitionException, TokenStreamException {

		Precondition p = null;

		try {      // for error handling
			{
				switch (LA(1)) {
				case LITERAL_pre: {
					match(LITERAL_pre);
					break;
				}
				case LITERAL_requires: {
					match(LITERAL_requires);
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			match(LCURLY);
			{
				_loop97:
				do {
					if ((_tokenSet_18.member(LA(1)))) {
						p = precondition();
						if (inputState.guessing == 0) {
							sc.addPreCondition(p);
						}
					} else {
						break _loop97;
					}

				} while (true);
			}
			match(RCURLY);
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_46);
			} else {
				throw ex;
			}
		}
	}

	public final void programStatement(
			/*ProgramClosure pc,*/  OS_Element cont
									  ) throws RecognitionException, TokenStreamException {

		ImportStatement        imp = null;
		AnnotationClause       a   = null;
		List<AnnotationClause> as  = new ArrayList<AnnotationClause>();
		AliasStatement         als = null;

		try {      // for error handling
			switch (LA(1)) {
			case LITERAL_from:
			case LITERAL_import: {
				imp = importStatement(cont);
				break;
			}
			case LITERAL_extend:
			case LITERAL_class:
			case ANNOT:
			case LITERAL_namespace: {
				{
					_loop131:
					do {
						if ((LA(1) == ANNOT)) {
							a = annotation_clause();
							if (inputState.guessing == 0) {
								as.add(a);
							}
						} else {
							break _loop131;
						}

					} while (true);
				}
				{
					switch (LA(1)) {
					case LITERAL_namespace: {
						namespaceStatement(new NamespaceStatementImpl(cont, cur), as);
						break;
					}
					case LITERAL_extend:
					case LITERAL_class: {
						classStatement(cont, cur, as);
						break;
					}
					default: {
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
				}
				break;
			}
			case LITERAL_alias: {
				als = aliasStatement(cont);
				break;
			}
			default: {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
	}

	public final void propertyStatement(
			PropertyStatement ps
									   ) throws RecognitionException, TokenStreamException {

		IdentExpression prop_name = null;
		TypeName        tn        = null;

		try {      // for error handling
			{
				switch (LA(1)) {
				case LITERAL_prop: {
					match(LITERAL_prop);
					break;
				}
				case LITERAL_property: {
					match(LITERAL_property);
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			prop_name = ident();
			if (inputState.guessing == 0) {
				ps.setName(prop_name);
			}
			{
				switch (LA(1)) {
				case TOK_COLON: {
					match(TOK_COLON);
					break;
				}
				case TOK_ARROW: {
					match(TOK_ARROW);
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			tn = typeName2();
			if (inputState.guessing == 0) {
				ps.setTypeName(tn);
			}
			match(LCURLY);
			{
				_loop313:
				do {
					switch (LA(1)) {
					case LITERAL_get: {
						match(LITERAL_get);
						{
							switch (LA(1)) {
							case SEMI: {
								match(SEMI);
								if (inputState.guessing == 0) {
									ps.addGet();
								}
								break;
							}
							case LCURLY: {
								sco = scope3(ps);
								if (inputState.guessing == 0) {
									ps.get_scope(sco);
								}
								break;
							}
							default: {
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
						}
						break;
					}
					case LITERAL_set: {
						match(LITERAL_set);
						{
							switch (LA(1)) {
							case SEMI: {
								match(SEMI);
								if (inputState.guessing == 0) {
									ps.addSet();
								}
								break;
							}
							case LCURLY: {
								sco = scope3(ps);
								if (inputState.guessing == 0) {
									ps.set_scope(sco);
								}
								break;
							}
							default: {
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
						}
						break;
					}
					default: {
						break _loop313;
					}
					}
				} while (true);
			}
			match(RCURLY);
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_21);
			} else {
				throw ex;
			}
		}
	}

	public final @Nullable IdentExpression ident() throws RecognitionException, TokenStreamException {
		IdentExpression id;

		Token r1 = null;
		id = null;

		try {      // for error handling
			r1 = LT(1);
			match(IDENT);
			if (inputState.guessing == 0) {
				id = new IdentExpressionImpl(r1, inputState.getFilename(), cur);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_8);
			} else {
				throw ex;
			}
		}
		return id;
	}

	public final void qualidentList(
			QualidentList qal
								   ) throws RecognitionException, TokenStreamException {

		Qualident qid;

		try {      // for error handling
			qid = qualident();
			if (inputState.guessing == 0) {
				qal.add(qid);
			}
			{
				_loop162:
				do {
					if ((LA(1) == COMMA)) {
						match(COMMA);
						qid = qualident();
						if (inputState.guessing == 0) {
							qal.add(qid);
						}
					} else {
						break _loop162;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_5);
			} else {
				throw ex;
			}
		}
	}

	public final @Nullable IExpression relationalExpression() throws RecognitionException, TokenStreamException {
		IExpression ee;

		ee = null;
		ExpressionKind e2 = null; // should never be null (below)
		IExpression    e3 = null;
		TypeName       tn = null;

		try {      // for error handling
			ee = shiftExpression();
			{
				if ((_tokenSet_7.member(LA(1))) && (_tokenSet_56.member(LA(2)))) {
					{
						_loop205:
						do {
							if ((_tokenSet_60.member(LA(1))) && (_tokenSet_18.member(LA(2)))) {
								{
									switch (LA(1)) {
									case LT_: {
										match(LT_);
										if (inputState.guessing == 0) {
											e2 = ExpressionKind.LT_;
										}
										break;
									}
									case GT: {
										match(GT);
										if (inputState.guessing == 0) {
											e2 = ExpressionKind.GT;
										}
										break;
									}
									case LE: {
										match(LE);
										if (inputState.guessing == 0) {
											e2 = ExpressionKind.LE;
										}
										break;
									}
									case GE: {
										match(GE);
										if (inputState.guessing == 0) {
											e2 = ExpressionKind.GE;
										}
										break;
									}
									default: {
										throw new NoViableAltException(LT(1), getFilename());
									}
									}
								}
								e3 = shiftExpression();
								if (inputState.guessing == 0) {
									ee = ExpressionBuilder.build(ee, e2, e3);
									ee.setType(new OS_BuiltinType(BuiltInTypes.Boolean));
								}
							} else {
								break _loop205;
							}

						} while (true);
					}
				} else if ((LA(1) == LITERAL_is_a) && (_tokenSet_61.member(LA(2)))) {
					match(LITERAL_is_a);
					tn = typeName2();
					if (inputState.guessing == 0) {
						ee = new TypeCheckExpressionImpl(ee, tn);
					}
				} else {
					throw new NoViableAltException(LT(1), getFilename());
				}

			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final QualidentList qualidentList2() throws RecognitionException, TokenStreamException {
		QualidentList qal;

		Qualident qid;
		qal = new QualidentListImpl();

		try {      // for error handling
			qid = qualident();
			if (inputState.guessing == 0) {
				qal.add(qid);
			}
			{
				_loop165:
				do {
					if ((LA(1) == COMMA)) {
						match(COMMA);
						qid = qualident();
						if (inputState.guessing == 0) {
							qal.add(qid);
						}
					} else {
						break _loop165;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_0);
			} else {
				throw ex;
			}
		}
		return qal;
	}

	public final @Nullable IExpression shiftExpression() throws RecognitionException, TokenStreamException {
		IExpression ee;

		ee = null;
		ExpressionKind e2 = null;
		IExpression    e3 = null;

		try {      // for error handling
			ee = additiveExpression();
			{
				_loop209:
				do {
					if (((LA(1) >= SL && LA(1) <= BSR)) && (_tokenSet_18.member(LA(2)))) {
						{
							switch (LA(1)) {
							case SL: {
								match(SL);
								if (inputState.guessing == 0) {
									e2 = ExpressionKind.LSHIFT;
								}
								break;
							}
							case SR: {
								match(SR);
								if (inputState.guessing == 0) {
									e2 = ExpressionKind.RSHIFT;
								}
								break;
							}
							case BSR: {
								match(BSR);
								if (inputState.guessing == 0) {
									e2 = ExpressionKind.BSHIFTR;
								}
								break;
							}
							default: {
								throw new NoViableAltException(LT(1), getFilename());
							}
							}
						}
						e3 = additiveExpression();
						if (inputState.guessing == 0) {
							ee = ExpressionBuilder.build(ee, e2, e3);
						}
					} else {
						break _loop209;
					}

				} while (true);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final Scope3 scope3(
			OS_Element parent
							  ) throws RecognitionException, TokenStreamException {
		Scope3 sc;

		sc = new Scope3Impl(parent);
		ClassStatement cls = null;

		try {      // for error handling
			match(LCURLY);
			docstrings(sc);
			{
				_loop81:
				do {
					if ((_tokenSet_31.member(LA(1)))) {
						{
							switch (LA(1)) {
							case LITERAL_extend:
							case LITERAL_class: {
								cls = classStatement(sc.getParent(), cur, null/*annotations*/);
								if (inputState.guessing == 0) {
									sc.add(cls);
								}
								break;
							}
							case LITERAL_continue: {
								match(LITERAL_continue);
								break;
							}
							case LITERAL_break: {
								match(LITERAL_break);
								break;
							}
							case LITERAL_return: {
								match(LITERAL_return);
								{
									boolean synPredMatched79 = false;
									if (((_tokenSet_18.member(LA(1))) && (_tokenSet_32.member(LA(2))))) {
										int _m79 = mark();
										synPredMatched79 = true;
										inputState.guessing++;
										try {
											{
												expression();
											}
										} catch (RecognitionException pe) {
											synPredMatched79 = false;
										}
										rewind(_m79);
										inputState.guessing--;
									}
									if (synPredMatched79) {
										{
											expr = expression();
										}
									} else if ((_tokenSet_33.member(LA(1))) && (_tokenSet_34.member(LA(2)))) {
									} else {
										throw new NoViableAltException(LT(1), getFilename());
									}

								}
								break;
							}
							case LITERAL_with: {
								withStatement(sc.getParent());
								break;
							}
							default:
								if ((_tokenSet_35.member(LA(1))) && (_tokenSet_36.member(LA(2)))) {
									statement(sc.statementClosure(), sc.getParent());
								} else if ((_tokenSet_18.member(LA(1))) && (_tokenSet_32.member(LA(2)))) {
									expr = expression();
									if (inputState.guessing == 0) {
										sc.statementWrapper(expr);
									}
								} else if ((LA(1) == LCURLY) && (_tokenSet_37.member(LA(2)))) {
									syntacticBlockScope(sc.getParent());
								} else {
									throw new NoViableAltException(LT(1), getFilename());
								}
							}
						}
						opt_semi();
					} else {
						break _loop81;
					}

				} while (true);
			}
			match(RCURLY);
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_38);
			} else {
				throw ex;
			}
		}
		return sc;
	}

	public final void syntacticBlockScope(
			OS_Element aParent
										 ) throws RecognitionException, TokenStreamException {

		SyntacticBlock        sb  = new SyntacticBlockImpl(aParent);
		SyntacticBlockContext ctx = null;

		try {      // for error handling
			if (inputState.guessing == 0) {
				ctx = new SyntacticBlockContext(sb, cur);
				sb.setContext(ctx);
				cur = ctx;
			}
			sco = scope3(sb);
			if (inputState.guessing == 0) {
				sb.scope(sco);
			}
			if (inputState.guessing == 0) {
				sb.postConstruct();
				cur = cur.getParent();
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_33);
			} else {
				throw ex;
			}
		}
	}

	public final @Nullable TypeAliasStatement typeAlias(
			OS_Element cont
													   ) throws RecognitionException, TokenStreamException {
		TypeAliasStatement cr;

		TypeAliasBuilder tab = new TypeAliasBuilder();
		cr = null;

		try {      // for error handling
			typeAlias2(tab);
			if (inputState.guessing == 0) {
				tab.setParent(cont);
				tab.setContext(cur);
				cr = tab.build();
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_23);
			} else {
				throw ex;
			}
		}
		return cr;
	}

	public final void statement(
			StatementClosure cr, OS_Element aParent
							   ) throws RecognitionException, TokenStreamException {

		Qualident      q = null;
		ExpressionList o = null;

		try {      // for error handling
			{
				switch (LA(1)) {
				case IDENT:
				case STRING_LITERAL:
				case CHAR_LITERAL:
				case NUM_INT:
				case NUM_FLOAT:
				case LBRACK:
				case LPAREN:
				case LCURLY:
				case PLUS:
				case MINUS:
				case INC:
				case DEC:
				case BNOT:
				case LNOT:
				case LITERAL_true:
				case LITERAL_false:
				case LITERAL_this:
				case LITERAL_null:
				case LITERAL_function:
				case LITERAL_procedure: {
					expr = assignmentExpression();
					if (inputState.guessing == 0) {
						cr.statementWrapper(expr);
					}
					break;
				}
				case LITERAL_if: {
					ifConditional(cr.ifConditional(aParent, cur));
					break;
				}
				case LITERAL_match: {
					matchConditional(cr.matchConditional(cur), aParent);
					break;
				}
				case LITERAL_case: {
					caseConditional(cr.caseConditional(cur));
					break;
				}
				case LITERAL_const:
				case LITERAL_var:
				case LITERAL_val: {
					varStmt(cr, aParent);
					break;
				}
				case LITERAL_while:
				case LITERAL_do: {
					whileLoop(cr);
					break;
				}
				case LITERAL_iterate: {
					frobeIteration(cr);
					break;
				}
				case LITERAL_construct: {
					constructExpression(cr);
					break;
				}
				case LITERAL_yield: {
					match(LITERAL_yield);
					expr = expression();
					if (inputState.guessing == 0) {
						cr.yield(expr);
					}
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			opt_semi();
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_39);
			} else {
				throw ex;
			}
		}
	}

	public final @Nullable TypeName typeName2() throws RecognitionException, TokenStreamException {
		TypeName cr;

		cr = null;

		try {      // for error handling
			switch (LA(1)) {
			case LITERAL_generic:
			case QUESTION: {
				cr = genericTypeName2();
				break;
			}
			case LITERAL_typeof: {
				cr = typeOfTypeName2();
				break;
			}
			case IDENT:
			case LITERAL_const:
			case LITERAL_in:
			case LITERAL_out:
			case LITERAL_ref: {
				cr = normalTypeName2();
				break;
			}
			case LITERAL_function:
			case LITERAL_procedure:
			case LITERAL_func:
			case LITERAL_proc: {
				cr = functionTypeName2();
				break;
			}
			default: {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return cr;
	}

	public final void typeAlias2(
			TypeAliasBuilder tab
								) throws RecognitionException, TokenStreamException {

		Qualident       q = null;
		IdentExpression i = null;

		try {      // for error handling
			match(LITERAL_type);
			match(LITERAL_alias);
			i = ident();
			if (inputState.guessing == 0) {
				tab.setIdent(i);
			}
			match(BECOMES);
			q = qualident();
			if (inputState.guessing == 0) {
				tab.setBecomes(q);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_23);
			} else {
				throw ex;
			}
		}
	}

	public final TypeOfTypeName typeOfTypeName2() throws RecognitionException, TokenStreamException {
		TypeOfTypeName tn;

		tn = new TypeOfTypeNameImpl(cur);

		try {      // for error handling
			match(LITERAL_typeof);
			xy = qualident();
			if (inputState.guessing == 0) {
				tn.typeOf(xy);
				tn.set(TypeModifiers.TYPE_OF);
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return tn;
	}

	public final @Nullable IExpression unaryExpression() throws RecognitionException, TokenStreamException {
		IExpression ee;

		ee = null;
		IExpression e3 = null;

		try {      // for error handling
			switch (LA(1)) {
			case INC: {
				match(INC);
				ee = unaryExpression();
				if (inputState.guessing == 0) {
					ee = new UnaryExpressionImpl(ExpressionKind.INCREMENT, ee);
				}
				break;
			}
			case DEC: {
				match(DEC);
				ee = unaryExpression();
				if (inputState.guessing == 0) {
					ee = new UnaryExpressionImpl(ExpressionKind.DECREMENT, ee);
				}
				break;
			}
			case MINUS: {
				match(MINUS);
				ee = unaryExpression();
				if (inputState.guessing == 0) {
					ee = new UnaryExpressionImpl(ExpressionKind.NEG, ee);
				}
				break;
			}
			case PLUS: {
				match(PLUS);
				ee = unaryExpression();
				if (inputState.guessing == 0) {
					ee = new UnaryExpressionImpl(ExpressionKind.POS, ee);
				}
				break;
			}
			case IDENT:
			case STRING_LITERAL:
			case CHAR_LITERAL:
			case NUM_INT:
			case NUM_FLOAT:
			case LBRACK:
			case LPAREN:
			case LCURLY:
			case BNOT:
			case LNOT:
			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_this:
			case LITERAL_null:
			case LITERAL_function:
			case LITERAL_procedure: {
				ee = unaryExpressionNotPlusMinus();
				break;
			}
			default: {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final @Nullable IExpression unaryExpressionNotPlusMinus() throws RecognitionException, TokenStreamException {
		IExpression ee;

		ee = null;
		IExpression e3 = null;

		try {      // for error handling
			switch (LA(1)) {
			case BNOT: {
				match(BNOT);
				ee = unaryExpression();
				if (inputState.guessing == 0) {
					ee = new UnaryExpressionImpl(ExpressionKind.BNOT, ee);
				}
				break;
			}
			case LNOT: {
				match(LNOT);
				ee = unaryExpression();
				if (inputState.guessing == 0) {
					ee = new UnaryExpressionImpl(ExpressionKind.LNOT, ee);
				}
				break;
			}
			case IDENT:
			case STRING_LITERAL:
			case CHAR_LITERAL:
			case NUM_INT:
			case NUM_FLOAT:
			case LBRACK:
			case LPAREN:
			case LCURLY:
			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_this:
			case LITERAL_null:
			case LITERAL_function:
			case LITERAL_procedure: {
				ee = postfixExpression();
				break;
			}
			default: {
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_7);
			} else {
				throw ex;
			}
		}
		return ee;
	}

	public final void varStmt(
			StatementClosure cr, OS_Element aParent
							 ) throws RecognitionException, TokenStreamException {

		VariableSequence vsq = null;
		TypeName         tn  = null;

		try {      // for error handling
			if (inputState.guessing == 0) {
				vsq = cr.varSeq(cur);
			}
			{
				switch (LA(1)) {
				case LITERAL_var: {
					match(LITERAL_var);
					break;
				}
				case LITERAL_const: {
					match(LITERAL_const);
					if (inputState.guessing == 0) {
						vsq.defaultModifiers(TypeModifiers.CONST);
					}
					break;
				}
				case LITERAL_val: {
					match(LITERAL_val);
					if (inputState.guessing == 0) {
						vsq.defaultModifiers(TypeModifiers.VAL);
					}
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			{
				varStmt_i3(vsq.next());
				{
					_loop137:
					do {
						if ((LA(1) == COMMA)) {
							match(COMMA);
							varStmt_i3(vsq.next());
						} else {
							break _loop137;
						}

					} while (true);
				}
				{
					switch (LA(1)) {
					case TOK_COLON: {
						match(TOK_COLON);
						tn = typeName2();
						if (inputState.guessing == 0) {
							vsq.setTypeName(tn);
						}
						break;
					}
					case IDENT:
					case STRING_LITERAL:
					case CHAR_LITERAL:
					case NUM_INT:
					case NUM_FLOAT:
					case LITERAL_extend:
					case LITERAL_class:
					case LBRACK:
					case LITERAL_const:
					case LPAREN:
					case LCURLY:
					case RCURLY:
					case LITERAL_type:
					case ANNOT:
					case LITERAL_namespace:
					case LITERAL_from:
					case LITERAL_import:
					case LITERAL_constructor:
					case LITERAL_ctor:
					case LITERAL_destructor:
					case LITERAL_dtor:
					case LITERAL_continue:
					case LITERAL_break:
					case LITERAL_return:
					case LITERAL_with:
					case LITERAL_post:
					case LITERAL_ensures:
					case LITERAL_def:
					case LITERAL_var:
					case LITERAL_val:
					case LITERAL_alias:
					case LITERAL_yield:
					case LITERAL_construct:
					case SEMI:
					case LITERAL_invariant:
					case LITERAL_access:
					case PLUS:
					case MINUS:
					case INC:
					case DEC:
					case BNOT:
					case LNOT:
					case LITERAL_true:
					case LITERAL_false:
					case LITERAL_this:
					case LITERAL_null:
					case LITERAL_function:
					case LITERAL_procedure:
					case LITERAL_if:
					case LITERAL_match:
					case LITERAL_case:
					case LITERAL_while:
					case LITERAL_do:
					case LITERAL_iterate:
					case LITERAL_prop:
					case LITERAL_property: {
						break;
					}
					default: {
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
				}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_24);
			} else {
				throw ex;
			}
		}
	}

	public final void varStmt_i(
			VariableStatement vs
							   ) throws RecognitionException, TokenStreamException {

		TypeName        tn = null;
		IdentExpression i  = null;

		try {      // for error handling
			i = ident();
			if (inputState.guessing == 0) {
				vs.setName(i);
			}
			{
				switch (LA(1)) {
				case TOK_COLON: {
					match(TOK_COLON);
					tn = typeName2();
					if (inputState.guessing == 0) {
						vs.setTypeName(tn);
					}
					break;
				}
				case LCURLY:
				case BECOMES:
				case COMMA: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
			{
				switch (LA(1)) {
				case BECOMES: {
					match(BECOMES);
					expr = expression();
					if (inputState.guessing == 0) {
						vs.initial(expr);
					}
					break;
				}
				case LCURLY:
				case COMMA: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_40);
			} else {
				throw ex;
			}
		}
	}

	public final void varStmt_i3(
			VariableStatement vs
								) throws RecognitionException, TokenStreamException {

		IdentExpression i = null;

		try {      // for error handling
			i = ident();
			if (inputState.guessing == 0) {
				vs.setName(i);
			}
			{
				switch (LA(1)) {
				case BECOMES: {
					match(BECOMES);
					expr = expression();
					if (inputState.guessing == 0) {
						vs.initial(expr);
					}
					break;
				}
				case IDENT:
				case TOK_COLON:
				case STRING_LITERAL:
				case CHAR_LITERAL:
				case NUM_INT:
				case NUM_FLOAT:
				case LITERAL_extend:
				case LITERAL_class:
				case LBRACK:
				case LITERAL_const:
				case LPAREN:
				case LCURLY:
				case RCURLY:
				case LITERAL_type:
				case ANNOT:
				case LITERAL_namespace:
				case LITERAL_from:
				case LITERAL_import:
				case COMMA:
				case LITERAL_constructor:
				case LITERAL_ctor:
				case LITERAL_destructor:
				case LITERAL_dtor:
				case LITERAL_continue:
				case LITERAL_break:
				case LITERAL_return:
				case LITERAL_with:
				case LITERAL_post:
				case LITERAL_ensures:
				case LITERAL_def:
				case LITERAL_var:
				case LITERAL_val:
				case LITERAL_alias:
				case LITERAL_yield:
				case LITERAL_construct:
				case SEMI:
				case LITERAL_invariant:
				case LITERAL_access:
				case PLUS:
				case MINUS:
				case INC:
				case DEC:
				case BNOT:
				case LNOT:
				case LITERAL_true:
				case LITERAL_false:
				case LITERAL_this:
				case LITERAL_null:
				case LITERAL_function:
				case LITERAL_procedure:
				case LITERAL_if:
				case LITERAL_match:
				case LITERAL_case:
				case LITERAL_while:
				case LITERAL_do:
				case LITERAL_iterate:
				case LITERAL_prop:
				case LITERAL_property: {
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_53);
			} else {
				throw ex;
			}
		}
	}

	public final void whileLoop(
			StatementClosure cr
							   ) throws RecognitionException, TokenStreamException {

		Loop        loop = cr.loop();
		LoopContext ctx;

		try {      // for error handling
			{
				switch (LA(1)) {
				case LITERAL_while: {
					match(LITERAL_while);
					if (inputState.guessing == 0) {
						loop.type(LoopTypes.WHILE);
					}
					expr = expression();
					if (inputState.guessing == 0) {
						loop.expr(expr);
					}
					if (inputState.guessing == 0) {
						ctx = new LoopContext(cur, loop);
						loop.setContext((LoopContext) ctx);
						cur = ctx;
					}
					sco = scope3(loop);
					if (inputState.guessing == 0) {
						loop.scope(sco);
					}
					break;
				}
				case LITERAL_do: {
					match(LITERAL_do);
					if (inputState.guessing == 0) {
						loop.type(LoopTypes.DO_WHILE);
					}
					if (inputState.guessing == 0) {
						ctx = new LoopContext(cur, loop);
						loop.setContext((LoopContext) ctx);
						cur = ctx;
					}
					sco = scope3(loop);
					if (inputState.guessing == 0) {
						loop.scope(sco);
					}
					match(LITERAL_while);
					expr = expression();
					if (inputState.guessing == 0) {
						loop.expr(expr);
					}
					break;
				}
				default: {
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_39);
			} else {
				throw ex;
			}
		}
	}

	public final void withStatement(
			OS_Element aParent
								   ) throws RecognitionException, TokenStreamException {

		WithStatement ws  = new WithStatementImpl(aParent);
		WithContext   ctx = null;

		try {      // for error handling
			match(LITERAL_with);
			varStmt_i(ws.nextVarStmt());
			{
				match(COMMA);
				varStmt_i(ws.nextVarStmt());
			}
			if (inputState.guessing == 0) {
				ctx = new WithContext(ws, cur);
				ws.setContext(ctx);
				cur = ctx;
			}
			sco = scope3(ws);
			if (inputState.guessing == 0) {
				ws.scope(sco);
			}
			if (inputState.guessing == 0) {
				ws.postConstruct();
				cur = cur.getParent();
			}
		} catch (RecognitionException ex) {
			if (inputState.guessing == 0) {
				reportError(ex);
				recover(ex, _tokenSet_33);
			} else {
				throw ex;
			}
		}
	}

}
