import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicLabelUI;

class VerticalLabelUI extends BasicLabelUI {
        private final AffineTransform transform;

        public VerticalLabelUI(AffineTransform transform) {
            this.transform = transform;
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setTransform(transform);
            super.paint(g2, c);
            g2.dispose();
        }
    }
