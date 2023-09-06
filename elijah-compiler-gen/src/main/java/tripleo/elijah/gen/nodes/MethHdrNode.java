/*
 * Elijjah compiler, copyright Tripleo <oluoluolu+elijah@gmail.com>
 *
 * The contents of this library are released under the LGPL licence v3,
 * the GNU Lesser General Public License text was downloaded from
 * http://www.gnu.org/licenses/lgpl.html from `Version 3, 29 June 2007'
 *
 */
/**
 *
 */
package tripleo.elijah.gen.nodes;

import org.eclipse.jdt.annotation.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.comp.GenBuffer;
import tripleo.elijah.gen.CompilerContext;
import tripleo.elijah.gen.Node;
import tripleo.elijah.gen.TypeRef;
import tripleo.elijah.lang.i.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.lang.impl.*;
import tripleo.elijah.util.NotImplementedException;

import java.util.Iterator;
import java.util.List;

/**
 * @author Tripleo(sb)
 *
 */
public class MethHdrNode implements Node {

	final public @NotNull   MethNameNode       methName;
	private final @Nullable TypeRef            returnType2;
	private final           Node               _parent;
	private final @NotNull  List<ArgumentNode> argument_types;
	private final           int                _code;
	public                  int                argCount;
	public @Nullable        TypeNameNode       returnType;

	public MethHdrNode(final Node parent, @NonNull final IdentExpression return_type, final String method_name, final @NotNull List<ArgumentNode> argument_types, final int code) {
		_parent             = parent;
		_code               = code;
		methName            = new MethNameNode(method_name, this);
		argCount            = argument_types.size();
		this.argument_types = argument_types;//.stream().map(ArgumentNode::make).collect(Collections);
		returnType          = new TypeNameNode(return_type);
		//
		returnType2 = null;
	}

	public MethHdrNode(final TypeRef retType, final Node parent, final String methodName, final @NotNull List<ArgumentNode> argumentTypes, final int code) {
		_parent        = parent;
		_code          = code;
		methName       = new MethNameNode(methodName, this);
		argCount       = argumentTypes.size();
		argument_types = argumentTypes;
		returnType2    = retType;
		//
		returnType = null;
	}

	public void addAnnotation(final AnnotationNode node) {
		NotImplementedException.raise();
	}

	public void addModifier(final TypeModifiers mod) {
		NotImplementedException.raise();
	}

	public @NotNull Iterable<ArgumentNode> ArgumentsIterator() {
		// TODO Auto-generated method stub
		return new Iterable<ArgumentNode>() {

			@Override
			public @NotNull Iterator<ArgumentNode> iterator() {

				return new Iterator<ArgumentNode>() {

					private int c = 0;

					@Override
					public boolean hasNext() {
						final MethHdrNode node = MethHdrNode.this;
						return c < node.argCount;
					}

					@Override
					public ArgumentNode next() {
						final MethHdrNode node = MethHdrNode.this;
						return node.argument(c++);
					}
				};
			}
		};
	}

	public ArgumentNode argument(final int c) {
		// TODO Auto-generated method stub
//		if (c>=argument_types.size()) return null;
		return (argument_types.get(c));
	}

	public TypeRef returnType() {
		return returnType2;
	}

	@Override
	public int getCode() {
		return _code;
	}

	public Node getParent() {
		return _parent;
	}

	public String genName() {
		return methName.genName;
	}

	public void GenMethHdr(final CompilerContext cctx, final GenBuffer gbn) {
		// TODO Auto-generated method stub

	}

	public void BeginMeth(final CompilerContext cctx, final GenBuffer gbn) {
		// TODO Auto-generated method stub

	}

	public void EndMeth(final CompilerContext cctx, final GenBuffer gbn) {
		// TODO Auto-generated method stub

	}

}
