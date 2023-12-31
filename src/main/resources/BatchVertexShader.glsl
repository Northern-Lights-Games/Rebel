#version 330 core
layout (location = 0) in vec2 v_pos;
layout (location = 1) in vec2 v_uv;
layout (location = 2) in float v_texindex;
layout (location = 3) in vec4 v_color;
layout (location = 4) in float v_thickness;




out vec2 f_uv;
out float f_texindex;
out vec4 f_color;
out vec2 f_origin;
out vec2 f_size;
out float f_thickness;

uniform mat4 v_model;
uniform mat4 v_view;
uniform mat4 v_projection;






void main() {

    f_texindex = v_texindex;
    f_uv = v_uv;
    f_color = v_color;
    f_thickness = v_thickness;


    gl_Position = v_projection * v_view * v_model * vec4(v_pos, 0.0, 1.0);
}