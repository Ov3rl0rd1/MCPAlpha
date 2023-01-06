#version 330 core

layout(location = 0) in vec3 position;

out vec3 color;

uniform mat4 view;
uniform mat4 projection;
uniform vec3 debugColor;

void main() {
	gl_Position = projection * view * vec4(position, 1.0);
	color = debugColor;
}