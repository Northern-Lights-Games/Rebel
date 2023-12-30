#version 330 core
out vec4 FragColor;

in vec2 f_uv;

uniform sampler2D u_textures[32];
in float f_texindex;
in vec4 f_color;
in float f_thickness;
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


        if(index == 0) FragColor += texture(u_textures[0], f_uv) * f_color;
        if(index == 1) FragColor += texture(u_textures[1], f_uv) * f_color;
        if(index == 2) FragColor += texture(u_textures[2], f_uv) * f_color;
        if(index == 3) FragColor += texture(u_textures[3], f_uv) * f_color;
        if(index == 4) FragColor += texture(u_textures[4], f_uv) * f_color;
        if(index == 5) FragColor += texture(u_textures[5], f_uv) * f_color;
        if(index == 6) FragColor += texture(u_textures[6], f_uv) * f_color;
        if(index == 7) FragColor += texture(u_textures[7], f_uv) * f_color;
        if(index == 8) FragColor += texture(u_textures[8], f_uv) * f_color;
        if(index == 9) FragColor += texture(u_textures[9], f_uv) * f_color;
        if(index == 10) FragColor += texture(u_textures[10], f_uv) * f_color;
        if(index == 11) FragColor += texture(u_textures[11], f_uv) * f_color;
        if(index == 12) FragColor += texture(u_textures[12], f_uv) * f_color;
        if(index == 13) FragColor += texture(u_textures[13], f_uv) * f_color;
        if(index == 14) FragColor += texture(u_textures[14], f_uv) * f_color;
        if(index == 15) FragColor += texture(u_textures[15], f_uv) * f_color;
        if(index == 16) FragColor += texture(u_textures[16], f_uv) * f_color;
        if(index == 17) FragColor += texture(u_textures[17], f_uv) * f_color;
        if(index == 18) FragColor += texture(u_textures[18], f_uv) * f_color;
        if(index == 19) FragColor += texture(u_textures[19], f_uv) * f_color;
        if(index == 20) FragColor += texture(u_textures[20], f_uv) * f_color;
        if(index == 21) FragColor += texture(u_textures[21], f_uv) * f_color;
        if(index == 22) FragColor += texture(u_textures[22], f_uv) * f_color;
        if(index == 23) FragColor += texture(u_textures[23], f_uv) * f_color;
        if(index == 24) FragColor += texture(u_textures[24], f_uv) * f_color;
        if(index == 25) FragColor += texture(u_textures[25], f_uv) * f_color;
        if(index == 26) FragColor += texture(u_textures[26], f_uv) * f_color;
        if(index == 27) FragColor += texture(u_textures[27], f_uv) * f_color;
        if(index == 28) FragColor += texture(u_textures[28], f_uv) * f_color;
        if(index == 29) FragColor += texture(u_textures[29], f_uv) * f_color;
        if(index == 30) FragColor += texture(u_textures[30], f_uv) * f_color;
        if(index == 31) FragColor += texture(u_textures[31], f_uv) * f_color;
    }

}
