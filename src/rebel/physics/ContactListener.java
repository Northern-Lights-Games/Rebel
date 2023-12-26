package rebel.physics;

import org.jbox2d.dynamics.contacts.Contact;

public interface ContactListener {

    void beginContact(Contact contact);

    void endContact(Contact contact);
}
