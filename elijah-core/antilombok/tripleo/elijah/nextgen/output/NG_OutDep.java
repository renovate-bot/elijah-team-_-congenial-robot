package tripleo.elijah.nextgen.output;

//import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tripleo.elijah.lang.i.OS_Module;
import tripleo.elijah.nextgen.comp_model.CM_Module;

//@Metadata(
//   mv = {1, 9, 0},
//   k = 1,
//   xi = 48,
//   d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0003H\u0016J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u000eHÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0013"},
//   d2 = {"Ltripleo/elijah/nextgen/output/NG_OutDep;", "Ltripleo/elijah/nextgen/comp_model/CM_Module;", "module_", "Ltripleo/elijah/lang/i/OS_Module;", "(Ltripleo/elijah/lang/i/OS_Module;)V", "getModule_", "()Ltripleo/elijah/lang/i/OS_Module;", "component1", "copy", "equals", "", "other", "", "getFilename", "", "getModule", "hashCode", "", "toString", "tripleo.elijah.core"}
//)
public final class NG_OutDep implements CM_Module {
   @NotNull
   private final OS_Module module_;

   public NG_OutDep(@NotNull OS_Module module_) {
      Intrinsics.checkNotNullParameter(module_, "module_");
//      super();
      this.module_ = module_;
   }

   @NotNull
   public final OS_Module getModule_() {
      return this.module_;
   }

   @NotNull
   public String getFilename() {
      String var10000 = this.module_.getFileName();
      Intrinsics.checkNotNullExpressionValue(var10000, "getFileName(...)");
      return var10000;
   }

   @NotNull
   public OS_Module getModule() {
      return this.module_;
   }

   @NotNull
   public final OS_Module component1() {
      return this.module_;
   }

   @NotNull
   public final NG_OutDep copy(@NotNull OS_Module module_) {
      Intrinsics.checkNotNullParameter(module_, "module_");
      return new NG_OutDep(module_);
   }

   // $FF: synthetic method
   public static NG_OutDep copy$default(NG_OutDep var0, OS_Module var1, int var2, Object var3) {
      if ((var2 & 1) != 0) {
         var1 = var0.module_;
      }

      return var0.copy(var1);
   }

   @NotNull
   public String toString() {
      return "NG_OutDep(module_=" + this.module_ + ")";
   }

   public int hashCode() {
      return this.module_.hashCode();
   }

   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof NG_OutDep)) {
         return false;
      } else {
         NG_OutDep var2 = (NG_OutDep)other;
         return Intrinsics.areEqual(this.module_, var2.module_);
      }
   }
}
