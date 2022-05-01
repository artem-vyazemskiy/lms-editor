package lmseditor.gui.component.answer;

import lmseditor.backend.question.component.answer.NumericalAnswer;
import lmseditor.backend.question.component.answer.ShortAnswer;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class NumericalAnswersPanel extends JPanel {

    private class NumericalAnswerPanel extends JPanel {

        private JTextField textField;
        private JLabel label;
        private JTextField toleranceField;
        private JButton removeButton;

        public NumericalAnswerPanel() {
            this.setLayout(new GridBagLayout());

            label = new JLabel("+-");
            textField = new JTextField();
            toleranceField = new JTextField();
            removeButton = new JButton("-");
            removeButton.addActionListener(new RemoveButtonEvent());

            GridBagConstraints gbc = new GridBagConstraints();

            gbc.gridy = 0; gbc.gridx = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            this.add(textField, gbc);

            gbc.gridy = 0; gbc.gridx = 1;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0;
            this.add(label, gbc);

            gbc.gridy = 0; gbc.gridx = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            this.add(toleranceField, gbc);

            gbc.gridy = 0; gbc.gridx = 3;
            gbc.fill = GridBagConstraints.NONE;
            gbc.weightx = 0;
            this.add(removeButton);

            ((AbstractDocument) textField.getDocument()).setDocumentFilter(new MyDocumentFilter());
            ((AbstractDocument) toleranceField.getDocument()).setDocumentFilter(new MyDocumentFilter());
        }

        public double getAnswer() {
            return Double.parseDouble(textField.getText());
        }

        public double getTolerance() {
            return Double.parseDouble(toleranceField.getText());
        }

        private class RemoveButtonEvent implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                answers.remove(NumericalAnswerPanel.this);
                NumericalAnswersPanel.this.updateUI();
            }
        }

    }

    private JLabel label;
    private JButton addButton;
    private List<NumericalAnswer> answerList;
    private Box answers;
    private JScrollPane answersScrollPane;

    public NumericalAnswersPanel(List<NumericalAnswer> answerList) {
        this.setLayout(new BorderLayout());

        this.answerList = answerList;

        label = new JLabel("Enter correct answers");

        addButton = new JButton("Add");
        addButton.addActionListener(new AddButtonEvent());

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.add(label);
        header.add(addButton);

        answers = Box.createVerticalBox();

        JPanel northAlignPanel = new JPanel(new BorderLayout());
        northAlignPanel.add(answers, BorderLayout.NORTH);
        answersScrollPane = new JScrollPane(northAlignPanel);

        this.add(header, BorderLayout.NORTH);
        this.add(answersScrollPane, BorderLayout.CENTER);

    }

    public void loadData() {
        answerList.clear();
        for(int i = 0; i < answers.getComponentCount(); i++) {
            NumericalAnswerPanel numericalAnswerPanel = (NumericalAnswerPanel) answers.getComponent(i);
            NumericalAnswer numericalAnswer = new NumericalAnswer();
            numericalAnswer.setAnswer(numericalAnswerPanel.getAnswer());
            numericalAnswer.setTolerance(numericalAnswerPanel.getTolerance());
            answerList.add(numericalAnswer);
        }
    }

    private class AddButtonEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            NumericalAnswerPanel numericalAnswerPanel = new NumericalAnswerPanel();
            answers.add(numericalAnswerPanel);
            NumericalAnswersPanel.this.updateUI();
        }
    }

    class MyDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            string = string.replaceAll("[^\\d\\.]", "");
            super.insertString(fb, offset, string, attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            text = text.replaceAll("[^\\d\\.]", "");
            super.replace(fb, offset, length, text, attrs);
        }
    }

}
