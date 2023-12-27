#version 430 core

layout (local_size_x = 1, local_size_y = 1, local_size_z = 1) in;
layout(rgba32f) uniform image2D imgOutput;


struct Line {
    vec2 start;
    vec2 end;
};



vec2 tValue(Line line, float t){
    return vec2(line.start.x + ((line.end.x - line.start.x) * t), line.start.y + ((line.end.y - line.start.y) * t));
}

vec2 cubicBezier(vec2 p0, vec2 p1, vec2 p2, vec2 p3, float t){


    Line a = Line(p0, p1);
    Line b = Line(p2, p3);
    Line c = Line(a.end, b.start);
    Line ab = Line(vec2(0, 0), vec2(0, 0));
    Line bc = Line(vec2(0, 0), vec2(0, 0));
    Line abToBc = Line(vec2(0, 0), vec2(0, 0));


    ab.start = tValue(a, t);
    ab.end = tValue(c, t);

    bc.start = tValue(c, t);
    bc.end = tValue(b, t);

    abToBc.start = tValue(ab, t);
    abToBc.end = tValue(bc, t);

    vec2 tracer = tValue(abToBc, t);

    return tracer;
}

void main() {

    ivec2 texelCoord = ivec2(gl_GlobalInvocationID.xy);


    for(float i = 0.0; i < 1; i+=0.001f){

        vec2 point = cubicBezier(vec2(100, 250), vec2(300, 50), vec2(550, 550), vec2(750, 250), i);

        if(distance(texelCoord.xy , point) < 1){
            vec4 value = vec4(texelCoord.x / 1000, texelCoord.y / 1000, 0.0, 1.0);
            imageStore(imgOutput, texelCoord, value);

        }
    }


}


