/*
 * Copyright 2000-2015 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jetbrains.python.debugger.concurrency.tool;

import com.intellij.util.ui.UIUtil;
import com.jetbrains.python.debugger.concurrency.tool.graph.GraphPresentation;
import com.jetbrains.python.debugger.concurrency.tool.graph.GraphSettings;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ConcurrencyNamesPanel extends JComponent {
  private final GraphPresentation myGraphPresentation;

  public ConcurrencyNamesPanel(GraphPresentation presentation) {
    myGraphPresentation = presentation;

    myGraphPresentation.registerListener(new GraphPresentation.PresentationListener() {
      @Override
      public void graphChanged(int padding, int size) {
        UIUtil.invokeLaterIfNeeded(new Runnable() {
          @Override
          public void run() {
            updateNames();
          }
        });
      }
    });
  }

  private static int getYLocation(int i) {
    return i * (GraphSettings.CELL_HEIGHT + GraphSettings.INTERVAL) + GraphSettings.INTERVAL;
  }

  private void updateNames() {
    removeAll();
    ArrayList<String> names = myGraphPresentation.getThreadNames();
    setSize(new Dimension(getWidth(), getYLocation(names.size())));
    for (int i = 0; i < names.size(); ++i) {
      JLabel label = new JLabel();
      label.setLocation(GraphSettings.INTERVAL, getYLocation(i));
      label.setSize(new Dimension(getWidth(), GraphSettings.INTERVAL));
      label.setText(names.get(i));
      add(label);
    }
    repaint();
  }
}
