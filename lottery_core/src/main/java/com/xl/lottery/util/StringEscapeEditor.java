package com.xl.lottery.util;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringEscapeUtils;


public class StringEscapeEditor extends PropertyEditorSupport {
	private boolean escapeHTML;
	private boolean escapeJavaScript;
	private boolean escapeSQL;

	public StringEscapeEditor() {
		super();
	}

	public StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript,
			boolean escapeSQL) {
		super();
		this.escapeHTML = escapeHTML;
		this.escapeJavaScript = escapeJavaScript;
		this.escapeSQL = escapeSQL;
	}

	@Override
	public void setAsText(String text) {
		if (text == null) {
			setValue(null);
		} else {
			String value = text;
			if (escapeHTML) {
				value = StringEscapeUtils.escapeHtml4(value);
			}
			if (escapeJavaScript) {
				value = StringEscapeUtils.escapeEcmaScript(value);
			}
			if (escapeSQL) {
				value = StringEscapeUtils.escapeCsv(value);
			}
			setValue(value);
		}
	}

	@Override
	public String getAsText() {
		Object value = getValue();
		return value != null ? value.toString() : "";
	}
}
