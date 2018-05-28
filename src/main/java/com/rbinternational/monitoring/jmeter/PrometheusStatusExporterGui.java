package com.rbinternational.monitoring.jmeter;

import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.visualizers.gui.AbstractListenerGui;

public class PrometheusStatusExporterGui extends AbstractListenerGui {

  /** */
  private static final long serialVersionUID = 1L;

  public PrometheusStatusExporterGui() {
    super();
    init();
  }

  @Override
  public String getStaticLabel() {
    return "Prometheus Status exporter";
  }

  @Override
  public String getLabelResource() {
    return getClass().getCanonicalName();
  }

  @Override
  public TestElement createTestElement() {
    TestElement te = new PrometheusStatusExporter();
    modifyTestElement(te);
    return te;
  }

  @Override
  public void modifyTestElement(TestElement te) {
    super.configureTestElement(te);
  }

  private void init() {
    setLayout(new BorderLayout(0, 5));
    setBorder(makeBorder());
    JTextArea info = new JTextArea();
    info.setEditable(false);
    info.setWrapStyleWord(true);
    info.setOpaque(false);
    info.setLineWrap(true);
    info.setColumns(20);

    JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
    jScrollPane1.setViewportView(info);
    jScrollPane1.setBorder(null);

    info.setText("Simple jMeter prometheus exporter");

    add(jScrollPane1, BorderLayout.CENTER);
  }
}
