package generator

import java.io.PrintWriter
import gensdk.Clazz
import org.fusesource.scalate.DefaultRenderContext
import org.fusesource.scalate.TemplateEngine
import java.io.File
import gensdk.Package

class PhpSDKGenerator extends SDKGenerator{
	val engine = new TemplateEngine
	def generate(pack: Package) {
		
		val dir = new File(".\\generated\\php");
    
		// attempt to create the directory here
		val successful = dir.mkdir();
		
		pack.getClazzes.foreach(generateClass)
	}

	def generateClass(clazz : Clazz){
		val file = "./resources/php/Class.ssp"

			
		val templ = engine.load(file)
		val buffer = new PrintWriter(".\\generated\\php\\" +clazz.name+".php")
		val context = new DefaultRenderContext("/", engine, buffer)
		context.attributes("className") = clazz.name
		context.attributes("methodNames") = clazz.getMethods.map{m => m.name }
		val out = templ.render(context)
		
		buffer.flush()
	}
}