package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JsHighlighter {

    private JsHighlighter() {}

    public static void highlight(WebDriver driver, WebElement el) {
        ((JavascriptExecutor) driver).executeScript(
                "try{                                              " +
                        "  const e = arguments[0];                          " +
                        "  e.scrollIntoView({block:'center',inline:'center'}); " +
                        "  const r = e.getBoundingClientRect();             " +
                        "  let box = document.getElementById('__selenium_highlight__');" +
                        "  if(!box){                                        " +
                        "    box = document.createElement('div');           " +
                        "    box.id='__selenium_highlight__';               " +
                        "    box.style.position='fixed';                    " +
                        "    box.style.pointerEvents='none';                " +
                        "    box.style.zIndex='2147483647';                 " +
                        "    box.style.border='3px solid yellow';           " +
                        "    box.style.borderRadius='4px';                  " +
                        "    box.style.boxShadow='0 0 0 2px rgba(255,255,0,.6)';" +
                        "    box.style.transition='all .1s ease';           " +
                        "    document.body.appendChild(box);                " +
                        "  }                                                " +
                        "  box.style.left  = (r.left - 2) + 'px';           " +
                        "  box.style.top   = (r.top  - 2) + 'px';           " +
                        "  box.style.width = Math.max(1, r.width) + 'px';   " +
                        "  box.style.height= Math.max(1, r.height)+ 'px';   " +
                        "  box.style.display='block';                       " +
                        "  clearTimeout(window.__selenium_highlight_to__);  " +
                        "  window.__selenium_highlight_to__ = setTimeout(()=>{ " +
                        "    if(box) box.style.display='none';              " +
                        "  }, 800);                                         " +
                        "}catch(e){}                                        ",
                el
        );
    }
}
