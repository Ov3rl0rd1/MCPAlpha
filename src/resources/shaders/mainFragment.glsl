#version 330 core

in vec3 passColor;
in vec2 passUv;

out vec4 outColor;

uniform sampler2D tex;

void main() {
	vec3 color = passColor;
	vec2 uv = passUv;
	outColor = texture(tex, uv) * vec4(vec3(color), 1.0);
}