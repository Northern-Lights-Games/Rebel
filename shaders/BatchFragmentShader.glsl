#version 330 core
out vec4 FragColor;

in vec2 f_texcoord;

uniform sampler2D u_textures[2];
in float f_texindex;

void main()
{
    int index = int(f_texindex);
    FragColor = texture(u_textures[index], f_texcoord);
}
