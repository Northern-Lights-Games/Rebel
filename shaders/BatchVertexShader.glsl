#version 330 core
layout (location = 0) in vec3 v_pos;
layout (location = 1) in vec2 v_texcoord;
layout (location = 2) in float v_texindex;
layout (location = 3) in vec4 v_color;



out vec2 f_texcoord;
out float f_texindex;
out vec4 f_color;

uniform mat4 v_model;
uniform mat4 v_view;
uniform mat4 v_projection;






void main() {

    f_texindex = v_texindex;
    f_texcoord = v_texcoord;
    f_color = v_color;

    gl_Position = v_projection * v_view * v_model * vec4(v_pos, 1.0);
}