#version 460 core

in vec3 position;
in vec3 color;
in vec2 uv;

out vec3 passColor;
out vec2 passUv;

uniform float sunlightIntensity = 15;
uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {
	gl_Position = projection * view * model * vec4(position, 1.0);

	float lightIntensity = clamp(sunlightIntensity / 15.0, 0.06, 1.0f) + color.x;

	passColor = vec3(lightIntensity);
	passUv = uv;
}