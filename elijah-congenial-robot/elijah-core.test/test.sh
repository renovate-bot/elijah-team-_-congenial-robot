/home/user/.jdks/graalvm-ce-17/bin/java \
-Dmaven.multiModuleProjectDirectory=/home/user/IdeaProjects/congenial-robot/elijah-core \
-Dfile.encoding=UTF-8 \
-Djansi.passthrough=true \
-Dmaven.home=/home/user/programs/idea-IC-232.9921.47/plugins/maven/lib/maven3 \
-Dclassworlds.conf=/home/user/programs/idea-IC-232.9921.47/plugins/maven/lib/maven3/bin/m2.conf \
-Dmaven.ext.class.path=/home/user/programs/idea-IC-232.9921.47/plugins/maven/lib/maven-event-listener.jar \
-javaagent:/home/user/programs/idea-IC-232.9921.47/lib/idea_rt.jar=33169:/home/user/programs/idea-IC-232.9921.47/bin \
-classpath /home/user/programs/idea-IC-232.9921.47/plugins/maven/lib/maven3/boot/plexus-classworlds-2.7.0.jar:/home/user/programs/idea-IC-232.9921.47/plugins/maven/lib/maven3/boot/plexus-classworlds.license \
org.codehaus.classworlds.Launcher \
-Didea.version=2023.2.5 \
-Dtest=tripleo.elijah.stages.gen_c.GenC_ArchUnit_Test \
test
