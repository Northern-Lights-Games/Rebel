package rebel.physics;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;
import rebel.Time;
import rebel.graphics.Rect2D;

import java.util.ArrayList;

public class Scene2D {
    private World world;
    private float accumulator = 0f;
    private float physicsToScreenScale;

    private int velocityIterations = 8;
    private int positionIterations = 2;

    private ArrayList<ContactListener> contactListeners = new ArrayList<>();


    public Scene2D(float physicsToScreenScale, Vector2f gravity){
        setPhysicsToScreenScale(physicsToScreenScale);
        world = new World(PhysicsUtil.toVec2(gravity));

        world.setContactListener(new org.jbox2d.callbacks.ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                for(ContactListener contactListener : contactListeners){
                    contactListener.beginContact(contact);
                }
            }

            @Override
            public void endContact(Contact contact) {
                for(ContactListener contactListener : contactListeners){
                    contactListener.endContact(contact);
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });
    }

    public void update(float step){
        //Prevent skipping the Box2D simulation too far into the future if the
        //Frame-rate gets slowed because of the Rendering side
        float frameTime = java.lang.Math.min(Time.deltaTime, 0.25f);
        accumulator += frameTime;

        while(accumulator >= step){
            world.step(step, velocityIterations, positionIterations);
            accumulator -= step;
        }

    }

    public int getVelocityIterations() {
        return velocityIterations;
    }

    public void setVelocityIterations(int velocityIterations) {
        this.velocityIterations = velocityIterations;
    }

    public int getPositionIterations() {
        return positionIterations;
    }

    public void setPositionIterations(int positionIterations) {
        this.positionIterations = positionIterations;
    }

    public RectBody2D newRectBody2D(Rect2D rect2D, RigidBody2D.Type bodyType){
        return new RectBody2D(world, rect2D.mul(getPhysicsToScreenScale()), bodyType);
    }

    public CircleBody2D newCircleBody2D(Vector2f pos, float radius, RigidBody2D.Type bodyType) {
        return new CircleBody2D(world, radius * getPhysicsToScreenScale(), pos.mul(getPhysicsToScreenScale()), bodyType);
    }

    public void addContactListener(ContactListener contactListener){
        contactListeners.add(contactListener);
    }

    public void removeContactListener(ContactListener contactListener){
        contactListeners.remove(contactListener);
    }

    public float getPhysicsToScreenScale() {
        return physicsToScreenScale;
    }

    public void setPhysicsToScreenScale(float physicsToScreenScale) {
        this.physicsToScreenScale = physicsToScreenScale;
    }

    public Vector2f getGravity(){
        return PhysicsUtil.toVector2f(world.getGravity());
    }

    public World getWorld() {
        return world;
    }
}
