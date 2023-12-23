#version 330 core
out vec4 FragColor;

in vec2 f_uv;

uniform sampler2D u_textures[32];
in float f_texindex;
in vec4 f_color;
in float f_thickness;
//gl_FragCoord goes y-axis up, but Rebel uses y-axis down...
layout(origin_upper_left) in vec4 gl_FragCoord;


void main()
{
    int index = int(f_texindex);


    if(index == -1){
        FragColor = f_color;
    }
    else if(index == -2){
        vec2 uv = f_uv * 2.0 - 1.0;

        float distance = length(uv);

        if(distance <= 1.0 && distance >= (1.0 - f_thickness))
            FragColor = f_color;
        else
            discard;

    }
    else {
        FragColor = texture(u_textures[index], f_uv) * f_color;
    }

}
