varying vec4 color;

void main(){
    gl_TexCoord[0] = gl_MultiTexCoord0;
    
	color = gl_Color.rgba;
	gl_Position = ftransform();
}